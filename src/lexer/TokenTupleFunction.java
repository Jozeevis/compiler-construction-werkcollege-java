package lexer;

import java.util.List;

public class TokenTupleFunction extends TokenField {
	public final TupleFunction type;

	public TupleFunction getType() {
		return type;
	}

	public TokenTupleFunction(TupleFunction type, int lineNumber) {
		super(TokenType.TOK_PRIM_FUNC_TUPLE, lineNumber);
		this.type = type;
	}

	@Override
	public void addCodeToStack(List<String> stack) {
		if (type == TupleFunction.TUPLEFUNC_FIRST) {
			stack.add("ldh -1\n");
		} else {
			stack.add("ldh 0\n");
		}
	}
}
