package tree.ast.expressions.num;

import java.util.List;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 *
 * @author Flip van Spaendonck and Lars Kuijpers
 */
public class Divide extends TwoArg {

    public Divide(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " / " + right + ")";
    }

    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        // x / 0 = undefined
        if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            //throw new ArithmeticException("Can't divide by zero");
            // TODO: This should throw an error right?
            return this;
        } 
        // 0 / x = 0
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
        	return new NumConstant(0);
        } 
        // x / 1 = x
        else if (right instanceof NumConstant && ((NumConstant)right).constant == 1) {
            return right;
        } 
        // x / y = x/y if x,y are constants
        else if (left instanceof NumConstant & right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant % ((NumConstant)right).constant);
        } 
        else {
        	return this;
        }
    }

	@Override
	public void addCodeToStack(List<String> stack) {
		left.addCodeToStack(stack);
		right.addCodeToStack(stack);
		stack.add("div");
	}
}
