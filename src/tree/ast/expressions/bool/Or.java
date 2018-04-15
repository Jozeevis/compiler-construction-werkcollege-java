/**
 * 
 */
package tree.ast.expressions.bool;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 * @author Flip van Spaendonck
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
		if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant || ((BoolConstant)right).constant);
		} else if (left instanceof BoolConstant && ((BoolConstant)left).constant) {
			return new BoolConstant(true);
		} else if (right instanceof BoolConstant && ((BoolConstant)right).constant) {
			return new BoolConstant(true);
		}
		return this;
	}

}
