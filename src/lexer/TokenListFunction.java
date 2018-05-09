package lexer;

public class TokenListFunction extends Token {
	ListFunction type;

	public ListFunction getType() {
		return type;
	}

	public TokenListFunction(ListFunction type) {
		super(TokenType.TOK_PRIM_FUNC_LIST);
		this.type = type;
	}
}
