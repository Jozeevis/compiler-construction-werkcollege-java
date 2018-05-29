package lexer;

public class TokenChar extends Token {
	public final char value;

	public char getValue() {
		return value;
	}

	public TokenChar(char value) {
		super(TokenType.TOK_CHAR);
		this.value = value;
	}

}
