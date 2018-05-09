package lexer;

public class TokenIdentifier extends Token {
	public final String value;

	//TODO: phase out getValue, it is now a public final variable.
	public String getValue() {
		return value;
	}

	public TokenIdentifier(String val) {
		super(TokenType.TOK_IDENTIFIER);
		value = val;
	}

}
