package tree.ast.expressions.num;

import java.util.Map;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit, Flip van Spaendonck and Lars Kuijpers
 */
public class Add extends TwoArg {

    public Add(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }

    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        // x + 0 = x
        if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            return left;
        } 
        // 0 + x = x
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
            return right;
        }
        // x + y = x+y if x,y are constants
        else if (left instanceof NumConstant && right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant + ((NumConstant)right).constant);
        } 
        else {
            return this;
        }
    }

	@Override
	public String getCode() {
		return "add";
	}
}
