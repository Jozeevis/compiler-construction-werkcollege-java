package tree;

/**
 * @author Flip van Spaendonck
 *
 */
public class SyntaxTree {

	public final SyntaxKnot root;
	
	public SyntaxKnot frontier;
	
	public SyntaxTree(SyntaxKnot root) {
		this.root = root;
		frontier = root;
	}
}
