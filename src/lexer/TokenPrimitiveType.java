package lexer;

public class TokenPrimitiveType extends Token {
	PrimitiveType type;

	public PrimitiveType getType() {
		return type;
	}

	public TokenPrimitiveType(PrimitiveType type) {
		super(TokenType.TOK_PRIM_TYPE);
		this.type = type;
	}
}
