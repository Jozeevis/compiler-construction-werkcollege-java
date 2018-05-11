package tree.ast.expressions.num;

import java.util.Map;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit, Flip van Spaendonck and Lars Kuijpers
 */
public class Multiply extends TwoArg {

    public Multiply(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " * " + right + ")";
    }

    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        // x * 0 = 0
        if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            return new NumConstant(0);
        } 
        // 0 * x = 0
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
            return new NumConstant(0);
        } 
        // x * 1 = x
        else if (right instanceof NumConstant && ((NumConstant)right).constant == 1) {
            return left;
        } 
        // 1 * x = x
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 1) {
            return right;
        }
        // x * y = x*y if x,y are constants
        else if (left instanceof NumConstant && right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant + ((NumConstant)right).constant);
        } 
        else {
            return this;
        }
    }

	@Override
	public String getCode() {
		return "mul";
	}
}
