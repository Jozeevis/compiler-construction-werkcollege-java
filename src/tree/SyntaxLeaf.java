package tree;

import lexer.Token;

/**
 * @author Flip van Spaendonck
 *
 */
public class SyntaxLeaf extends SyntaxNode{
	
	public final Token leaf;
	
	public SyntaxLeaf(Token token, SyntaxKnot parent) {
		super(parent);
		
		leaf = token;
	}

}
