package lexer;

public class TokenInteger extends Token {
	int value;

	public int getValue() {
		return value;
	}

	public TokenInteger(int val) {
		super(TokenType.TOK_INT);
		this.value = val;
	}
}
