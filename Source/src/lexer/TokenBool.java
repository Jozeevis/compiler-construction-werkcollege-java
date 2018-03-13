package lexer;

public class TokenBool extends Token {
	boolean value;

	public boolean getValue() {
		return value;
	}

	public TokenBool(boolean value) {
		super(TokenType.TOK_BOOL);
		this.value = value;
	}
}
