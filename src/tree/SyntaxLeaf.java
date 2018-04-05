package tree;

import lexer.Token;

public class SyntaxLeaf extends SyntaxNode{
	
	public final Token leaf;
	
	public SyntaxLeaf(Token token, SyntaxKnot parent) {
		super(parent);
		
		leaf = token;
	}

}
