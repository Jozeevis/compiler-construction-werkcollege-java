package parser;

import util.Visitor;
import lexer.Token;
import lexer.TokenInteger;

public class AstExprInteger extends AstExpr {
	private int value;

	public int getValue() {
		return value;
	}

	public AstExprInteger(int value) {
		this.value = value;
	}

	// Caller must make sure that Token is a TokenInteger
	public AstExprInteger(Token tok) {
		this.value = ((TokenInteger) tok).getValue();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AstExprInteger other = (AstExprInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
