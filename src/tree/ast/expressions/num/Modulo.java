package tree.ast.expressions.num;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @authorFlip van Spaendonck
 */
public class Modulo extends TwoArg {

    public Modulo(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " % " + right + ")";
    }

    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        if (left instanceof NumConstant & right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant % ((NumConstant)right).constant);
        } else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
        	return new NumConstant(0);
        } else if (left instanceof NumConstant && ((NumConstant)left).constant == 1) {
        	return right;
        } else if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
        	//NONONONONONONONONONONONONO
        	return this;
        } else {
        	return this;
        }
    }

	@Override
	public String getCode() {
		return "mod";
	}
}
