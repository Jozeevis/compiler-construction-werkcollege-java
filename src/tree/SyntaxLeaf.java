package tree;

import lexer.Token;

/**
 * A leaf in the syntax-tree data structure.
 * @author Flip van Spaendonck
 *
 */
public class SyntaxLeaf extends SyntaxNode{
	
	/** The token that is represented by this leaf**/
	public final Token leaf;
	
	public SyntaxLeaf(Token token, SyntaxKnot parent) {
		super(parent);
		
		leaf = token;
	}

}
