/**
 * 
 */
package tree.ast.expressions.bool;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;
import tree.ast.expressions.num.NumConstant;

/**
 * @author Flip van Spaendonck
 *
 */
public class LargerEq extends TwoArg {

	public LargerEq(BaseExpr left, BaseExpr right) {
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
			return new BoolConstant(((NumConstant)left).constant >= ((NumConstant)right).constant);
		}
		return this;
	}

	@Override
	public String getCode() {
		return "le";
	}

}
