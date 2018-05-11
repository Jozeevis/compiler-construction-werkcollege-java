package tree.ast.expressions.num;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Flip van Spaendonck and Lars Kuijpers
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
        // x % 0 = undefined
        if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            throw new ArithmeticException("Can't modulo by zero");
            // TODO: This should throw an error right?
            //return this;
        }
        // 0 % x = 0
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
        	return new NumConstant(0);
        }
        // TODO: is this correct?
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 1) {
        	return right;
        }
        // x % y = x%y if x,y are constants
        else if (left instanceof NumConstant & right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant % ((NumConstant)right).constant);
        }
        else {
        	return this;
        }
    }

	@Override
	public String getCode() {
		return "mod";
	}
}
