package tree;

/**
 * A data-tree representing a syntactically correct program code.
 * @author Flip van Spaendonck
 *
 */
public class SyntaxTree {

	/** The root of this data-tree**/
	public final SyntaxKnot root;
	/** The bottom-left most knot in this tree that has not yet been completely filled, is null when the tree has been completely filled.**/
	public SyntaxKnot frontier;
	
	public SyntaxTree(SyntaxKnot root) {
		this.root = root;
		frontier = root;
	}
	
	public void addToFrontier(SyntaxNode node) {
		
	}
}
