package lexer;

public class TokenInteger extends Token {
	public final int value;

	public int getValue() {
		return value;
	}

	public TokenInteger(int val, int lineNumber) {
		super(TokenType.TOK_INT, lineNumber);
		this.value = val;
	}
}
