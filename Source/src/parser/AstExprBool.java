package parser;

import lexer.Token;
import lexer.TokenBool;
import util.Visitor;

public class AstExprBool extends AstExpr {
	boolean value;

	public boolean getValue() {
		return value;
	}

	public AstExprBool(boolean value) {
		this.value = value;
	}

	public AstExprBool(Token tok) {
		this.value = ((TokenBool) tok).getValue();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		AstExprBool other = (AstExprBool) obj;
		if (value != other.value)
			return false;
		return true;
	}
}
