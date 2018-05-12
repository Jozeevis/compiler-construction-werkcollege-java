package lexer;

public class TokenTupleFunction extends TokenField {
	public final TupleFunction type;

	public TupleFunction getType() {
		return type;
	}

	public TokenTupleFunction(TupleFunction type) {
		super(TokenType.TOK_PRIM_FUNC_TUPLE);
		this.type = type;
	}
}
