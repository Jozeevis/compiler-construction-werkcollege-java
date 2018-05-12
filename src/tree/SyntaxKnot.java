/**
 * 
 */
package tree;

import grammar.Expression;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class SyntaxKnot extends SyntaxNode {

	/** The SyntaxNodes used to fill in the previously described expression**/
	public final SyntaxNode[] children;
	/** This int is used to check how much of this node's children have already been added**/
	private int arrayIndex = 0;
	
	public SyntaxKnot(SyntaxKnot parent) {
		super(parent);
		children = initializeChildrenArray();
	}

	protected abstract SyntaxNode[] initializeChildrenArray();

	/**
	 * Adds the given syntaxnode to this node as a child.
	 * @param child
	 */
	public void addChild(SyntaxNode child) {
		children[arrayIndex++] = child;
	}
	
	/**
	 * Checks whether the amount of children this node has, is equal to the amount of children allowed by the expression.
	 */
	public boolean isComplete() {
		return (arrayIndex == children.length);
	}

	public SyntaxNode[] getChildren() {
		return children;
	}

}
