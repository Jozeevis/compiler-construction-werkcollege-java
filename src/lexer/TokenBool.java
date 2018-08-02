package lexer;

public class TokenBool extends Token {
	public final boolean value;

	public boolean getValue() {
		return value;
	}

	public TokenBool(boolean value, int lineNumber) {
		super(TokenType.TOK_BOOL, lineNumber);
		this.value = value;
	}
}
