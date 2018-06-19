package tree;

import java.util.LinkedList;
import java.util.List;

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
	public int nrOfGlobals;
	public String mainAddress;
	
	public SyntaxTree(SyntaxKnot root) {
		this.root = root;
		frontier = root;
	}
	
	@Override
	public String toString() {
		return printChildren(root, 0);
	}
	
	private String printChildren(SyntaxNode node, int nrOfIndents) {
		String out = "";
		for(int i=0; i<nrOfIndents; i++)
			out+= "    ";
		out+= node + "\n";
		if (node instanceof SyntaxKnot) {
			for(SyntaxNode child: ((SyntaxKnot) node).children) {
				out+= printChildren(child, nrOfIndents+1);
			}
		}
		return out;
	}
}
