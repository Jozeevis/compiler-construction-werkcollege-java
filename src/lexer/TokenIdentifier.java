package lexer;

public class TokenIdentifier extends Token {
	public final String value;

	//TODO: phase out getValue, it is now a public final variable.
	public String getValue() {
		return value;
	}

	public TokenIdentifier(String val, int lineNumber) {
		super(TokenType.TOK_IDENTIFIER, lineNumber);
		value = val;
	}
	
	@Override
	public String toString() {
		return "id:"+value;
	}

}
