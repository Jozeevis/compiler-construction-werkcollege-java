package tree;

import lexer.Token;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class SyntaxNode {

	public final int depth;
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
