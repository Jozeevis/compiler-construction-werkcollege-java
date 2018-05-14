package tree;

import java.util.List;

import grammar.Expression;
import tree.ast.LabelCounter;

/**
 * A knot in the syntax-tree data structure.
 * A syntax expression knot does not hold an ALU expression but a grammatical expression from the Grammar package.
 * @author Flip van Spaendonck
 */
public class SyntaxExpressionKnot extends SyntaxKnot{

	/** The expression that this node represents**/
	public Expression expression;
	
	public SyntaxExpressionKnot(Expression expression, SyntaxKnot frontier) {
		super(frontier);
		
		this.expression = expression;
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return  new SyntaxExpressionKnot[expression.expression.length];
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		for(SyntaxNode child : children) {
			child.addCodeToStack(stack, counter);
		}
	}
}
