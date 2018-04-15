package tree.ast.expressions.num;

import java.util.Map;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Multiply extends TwoArg {

    public Multiply(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " * " + right + ")";
    }

    /**
     * n * m = n*m with n, m being constant
     * 0 * y = 0 
     * 1 * y = y 
     * x * 0 = 0 
     * x * 1 = x
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
            return new NumConstant(0);
        } else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
            return new NumConstant(0);
        } else if (right instanceof NumConstant && ((NumConstant)right).constant == 1) {
            return left;
        } else if (left instanceof NumConstant && ((NumConstant)left).constant == 1) {
            return right;
        } else {
            return this;
        }
    }
}
