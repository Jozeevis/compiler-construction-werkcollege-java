package tree;

import grammar.Expression;

/**
 * A knot in the syntax-tree data structure.
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
}
