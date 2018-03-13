package lexer;

public class TokenIdentifier extends Token {
	final String value;

	public String getValue() {
		return value;
	}

	public TokenIdentifier(String val) {
		super(TokenType.TOK_IDENTIFIER);
		value = val;
	}

}
