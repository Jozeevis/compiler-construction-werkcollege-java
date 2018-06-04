package tree.ast;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing an if-(else) statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class IfElseStmtKnot extends ASyntaxKnot implements ITypeCheckable{
	
	/** The TokenExpression that denotes the boolean expression that is checked in the if **/
	public final BaseExpr check;
	/** The TokenExpression that denotes the function body if the check expression is true **/
	public final SyntaxNode ifBody;
	/** The TokenExpression that denotes the function body if the check expression is false **/
	public final SyntaxNode elseBody;
	
	public IfElseStmtKnot(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) throws Exception {
		super(parent);

		check = ((TokenExpression) oldKnot.children[2].reduceToToken()).expression;
		ifBody = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[5]).root;
		// Check if there is an else to this if-statement
		if (oldKnot.children.length > 6) {
			elseBody = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[8]).root;
		}
		else {
			elseBody = null;
		}
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type checkType;
		if (!(checkType = check.checkTypes(domain)).equals(BaseType.instanceBool)) {
			throw new TypeException("Check was of type: "+checkType+", while type Bool was expected.");
		}
		return domain;
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		if (elseBody != null)
			return new SyntaxNode[] { ifBody, elseBody};
		return new SyntaxNode[] {ifBody};
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Generate the check expression body
		check.addCodeToStack(stack, counter);
		// Number that will be used for all labels in this statement
		counter.incr();
		// Check if the condition is false, if so jump to elselabel
		stack.add("brf ELSELABEL" + counter.getCount());

		// Add the code for the ifbody
		ifBody.addCodeToStack(stack, counter);
		// Skip the elsebody
		stack.add("bra ENDLABEL" + counter.getCount());

		// Label used when the condition is false to skip the if-body
		stack.add("ELSELABEL" + counter.getCount() + ": nop");
		// Add the code for the elsebody
		elseBody.addCodeToStack(stack, counter);
		
		// Label used when the condition is true to skip the else-body
		stack.add("ENDLABEL" + counter.getCount() + ": nop");
	}

}
