package lexer;

public class TokenPrimitiveType extends Token {
	PrimitiveType type;

	public PrimitiveType getType() {
		return type;
	}

	public TokenPrimitiveType(PrimitiveType type, int lineNumber) {
		super(TokenType.TOK_PRIM_TYPE, lineNumber);
		this.type = type;
	}
}
