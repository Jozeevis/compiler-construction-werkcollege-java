package tree.ast;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.IDDeclarationBlock.Scope;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a while statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class WhileStmtKnot extends ASyntaxKnot {
	/** The TokenExpression that denotes the boolean expression that is checked in the while **/
	public final BaseExpr check;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;
	
	public WhileStmtKnot(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) throws Exception {
		super(parent);

		check = ((TokenExpression) oldKnot.children[2].reduceToToken()).expression;
		body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[5]).root;
		children = new SyntaxNode[] {body};
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		Type checkType;
		if (!(checkType = check.checkTypes(domain)).equals(BaseType.instanceBool)) {
			throw new TypeException("Check was of type: "+checkType+", while type Bool was expected.");
		}
		body.checkTypes(domain, scope);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Number that will be used for all labels in this statement
		counter.incr();

		// Label for checking the condition loop
		stack.add("CHECKLABEL" + counter.getCount());
		// Generate the check expression body
		check.addCodeToStack(stack, counter);
		// If the condition is false, jump out of the loop
		stack.add("brf ENDLABEL" + counter.getCount());

		body.addCodeToStack(stack, counter);
		// Jump back to the while check
		stack.add("bra CHECKLABEL" + counter.getCount());

		// Label for jumping out of the loop
		stack.add("ENDLABEL" + counter.getCount() + ": nop");
	}

	@Override
	public boolean alwaysReturns() {
		return body.alwaysReturns();
	}

}
