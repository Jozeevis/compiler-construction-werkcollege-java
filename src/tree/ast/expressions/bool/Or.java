/**
 * 
 */
package tree.ast.expressions.bool;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class Or extends TwoArg {

	public Or(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		// true || x = true
		if (left instanceof BoolConstant && ((BoolConstant)left).constant == true) {
			return new BoolConstant(true);
		} 
		// x || true = x
		else if (right instanceof BoolConstant && ((BoolConstant)right).constant == true) {
			return new BoolConstant(true);
		}
		// x || y = x||y if x,y are constants
		else if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant || ((BoolConstant)right).constant);
		}
		return this;
	}

	@Override
	public String getCode() {
		return "or";
	}

}
