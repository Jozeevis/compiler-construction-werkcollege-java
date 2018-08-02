package lexer;

public class Token {
	private final TokenType tokenType;
	public final int lineNumber;

	public Token(TokenType tokType, int lineNumber) {
		tokenType = tokType;
		this.lineNumber = lineNumber;
	}

	public TokenType getTokenType() {
		return tokenType;
	}
	
	@Override
	public String toString() {
		return tokenType.name();
	}
}
