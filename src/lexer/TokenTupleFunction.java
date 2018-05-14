package lexer;

import java.util.List;

public class TokenTupleFunction extends TokenField {
	public final TupleFunction type;

	public TupleFunction getType() {
		return type;
	}

	public TokenTupleFunction(TupleFunction type) {
		super(TokenType.TOK_PRIM_FUNC_TUPLE);
		this.type = type;
	}

	@Override
	public void addCodeToStack(List<String> stack) {
		if (type == TupleFunction.TUPLEFUNC_FIRST) {
			stack.add("ldh -1");
		} else {
			stack.add("ldh 0");
		}
	}
}
