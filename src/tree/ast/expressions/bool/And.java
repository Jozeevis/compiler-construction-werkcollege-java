/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class And extends TwoArg {

	public And(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		// false && x = false
		if (left instanceof BoolConstant && ((BoolConstant)left).constant == false) {
			return new BoolConstant(false);
		} 
		// x && false = false
		else if (right instanceof BoolConstant && ((BoolConstant)right).constant == false) {
			return new BoolConstant(false);
		}
		// x && y = x&&y if x,y are constants
		else if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant && ((BoolConstant)right).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("and");
	}

}
