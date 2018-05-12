package tree.ast;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.TreeProcessing;
import tree.ast.types.BaseType;

/**
 * An abstract syntax knot representing a while statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class WhileStmtKnot extends ASyntaxKnot implements ITypeCheckable {
	/** The TokenExpression that denotes the boolean expression that is checked in the while **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;
	
	public WhileStmtKnot(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) throws Exception {
		super(parent);

		check = (TokenExpression) oldKnot.children[2].reduceToToken();
		body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[5]).root;
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		if (!check.type.equals(new BaseType(PrimitiveType.PRIMTYPE_BOOL)))
			return false;
		return check.expression.checkTypes(domain);
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[] {body};
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Number that will be used for all labels in this statement
		counter.incr();

		// Label for checking the condition loop
		stack.add("CHECKLABEL" + counter.getCount());
		// Generate the check expression body
		check.expression.addCodeToStack(stack);
		// If the condition is false, jump out of the loop
		stack.add("brf ENDLABEL" + counter.getCount());

		body.addCodeToStack(stack, counter);
		// Jump back to the while check
		stack.add("bra CHECKLABEL" + counter.getCount());

		// Label for jumping out of the loop
		stack.add("ENDLABEL" + counter.getCount() + ": nop");
	}

}
