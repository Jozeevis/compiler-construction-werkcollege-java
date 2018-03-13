package lexer;

public class Token {
	private final TokenType tokenType;

	public Token(TokenType tokType) {
		tokenType = tokType;
	}

	public TokenType getTokenType() {
		return tokenType;
	}
}
