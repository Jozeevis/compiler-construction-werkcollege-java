package tree;

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
}
