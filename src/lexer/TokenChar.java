package lexer;

public class TokenChar extends Token {
	public final char value;

	public char getValue() {
		return value;
	}

	public TokenChar(char value, int lineNumber) {
		super(TokenType.TOK_CHAR, lineNumber);
		this.value = value;
	}

	@Override
	public String toString() {
		return ""+value;
	}
}
