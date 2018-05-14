/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;
import tree.ast.expressions.num.NumConstant;

/**
 * @author Flip van Spaendonck
 *
 */
public class Larger extends TwoArg {

	public Larger(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		if (left instanceof NumConstant & right instanceof NumConstant) {
			return new BoolConstant(((NumConstant)left).constant > ((NumConstant)right).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("gt");
	}

}
