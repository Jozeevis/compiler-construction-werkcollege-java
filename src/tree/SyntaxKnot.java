package tree;

import grammar.Expression;

/**
 * A knot in the syntax-tree data structure.
 * @author Flip van Spaendonck
 */
public class SyntaxKnot extends SyntaxNode implements IKnot{

	/** The expression that this node represents**/
	public Expression expression;
	/** The SyntaxNodes used to fill in the previously described expression**/
	public SyntaxNode[] children;
	/** This int is used to check how much of this node's children have already been added**/
	private int arrayIndex = 0;
	
	public SyntaxKnot(Expression expression, SyntaxKnot parent) {
		super(parent);
		
		this.expression = expression;
		children = new SyntaxKnot[expression.expression.length];
	}

	/**
	 * Adds the given syntaxnode to this node as a child.
	 * @param child
	 */
	public void add(SyntaxNode child) {
		children[arrayIndex++] = child;
	}
	
	/**
	 * Checks whether the amount of children this node has, is equal to the amount of children allowed by the expression.
	 */
	public boolean isComplete() {
		return (arrayIndex == children.length);
	}

	@Override
	public SyntaxNode[] getChildren() {
		return children;
	}
}
