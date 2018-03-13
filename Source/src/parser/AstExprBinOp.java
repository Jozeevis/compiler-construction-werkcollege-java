package parser;

import util.Visitor;
import lexer.TokenType;

public class AstExprBinOp extends AstExpr {
	private AstExpr left;
	private TokenType operator;
	private AstExpr right;

	public AstExpr getLeft() {
		return left;
	}

	public TokenType getOperator() {
		return operator;
	}

	public AstExpr getRight() {
		return right;
	}

	public AstExprBinOp(AstExpr lhs, TokenType op, AstExpr rhs) {
		this.left = lhs;
		this.operator = op;
		this.right = rhs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		AstExprBinOp other = (AstExprBinOp) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (operator != other.operator)
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
