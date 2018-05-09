package tree.ast.expressions.num;

import java.util.Map;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Add extends TwoArg {

    public Add(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }

    /**
     * n + m = n + m if n,m are constant
     * x + 0 = x
     * 0 + y = y
     *
     * @return BaseExpr
     */
    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        if (left instanceof NumConstant && right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant + ((NumConstant)right).constant);
        } else if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            return left;
        } else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
            return right;
        } else {
            return this;
        }
    }

	@Override
	public String getCode() {
		return "add";
	}
}
