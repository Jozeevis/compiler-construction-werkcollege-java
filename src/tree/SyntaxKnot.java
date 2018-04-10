package tree;

import grammar.Expression;

/**
 * @author Flip van Spaendonck
 *
 */
public class SyntaxKnot extends SyntaxNode {

	public Expression expression;
	public SyntaxNode[] children;
	private int arrayIndex = 0;
	
	public SyntaxKnot(Expression expression, SyntaxKnot parent) {
		super(parent);
		
		this.expression = expression;
		children = new SyntaxKnot[expression.nrOfNodes];
	}

	public void add(SyntaxNode child) {
		children[arrayIndex++] = child;
	}
	
	public boolean isComplete() {
		return (arrayIndex == children.length);
	}
}
