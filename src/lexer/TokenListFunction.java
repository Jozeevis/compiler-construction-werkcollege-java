package lexer;

import java.util.List;

public class TokenListFunction extends TokenField {
	public final ListFunction type;

	public ListFunction getType() {
		return type;
	}

	public TokenListFunction(ListFunction type) {
		super(TokenType.TOK_PRIM_FUNC_LIST);
		this.type = type;
	}

	@Override
	public void addCodeToStack(List<String> stack) {
		// TODO Decide on what to do with lists.
	}
}
