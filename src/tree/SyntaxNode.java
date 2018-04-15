package tree;

import lexer.Token;

/**
 * An abstract class describing all nodes that are used in the syntax-tree data structure.
 * @author Flip van Spaendonck
 *
 */
public abstract class SyntaxNode {

	/** The depth at which this node is placed in the syntax-tree **/
	public final int depth;
	/** The syntax-knot above this node, is null when this node is the tree's root**/
	public final SyntaxKnot parent;
	
	protected SyntaxNode(SyntaxKnot parent) {
		if (parent == null) {
			depth = 0;
			this.parent = null;
		} else {
			depth = parent.depth+1;
			this.parent = parent;
		}
	}
	
	/**
	 * This method descends through this node and its most left child until a SyntaxLeaf is found, and its token is returned.
	 * @return the token held by the bottom-left-most syntax-leaf
	 */
	public Token reduceToToken() {
		SyntaxNode node = this;
		while(node instanceof SyntaxKnot) {
			node = ((SyntaxKnot) node).children[0];
		}
		if (node instanceof SyntaxLeaf) {
			return ((SyntaxLeaf) node).leaf;
		}
		
		return null;
	}
}
