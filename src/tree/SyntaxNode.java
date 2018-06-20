package tree;

import java.util.List;

import lexer.Token;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock.Scope;
import tree.ast.LabelCounter;

/**
 * An abstract class describing all nodes that are used in the syntax-tree data structure.
 * @author Flip van Spaendonck
 *
 */
public abstract class SyntaxNode {

	/** The depth at which this node is placed in the syntax-tree **/
	public final int depth;
	/** The syntax-knot above this node, is null when this node is the tree's root**/
	public SyntaxKnot parent;
	
	protected SyntaxNode(SyntaxKnot parent2) {
		if (parent2 == null) {
			depth = 0;
			this.parent = null;
		} else {
			depth = parent2.depth+1;
			this.parent = parent2;
		}
	}
	
	/**
	 * This method descends through this node and its most left child until a SyntaxLeaf is found, and its token is returned.
	 * @return the token held by the bottom-left-most syntax-leaf
	 */
	public Token reduceToToken() {
		SyntaxNode node = this;
		while(node instanceof SyntaxExpressionKnot) {
			node = ((SyntaxExpressionKnot) node).children[0];
		}
		if (node instanceof SyntaxLeaf) {
			return ((SyntaxLeaf) node).leaf;
		}
		
		return null;
	}

	public abstract void addCodeToStack(List<String> stack, LabelCounter counter);

	public abstract void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException;
	
	public abstract SyntaxNode getASTEquivalent(SyntaxKnot parent) throws Exception;
	
	public abstract boolean alwaysReturns();
}
