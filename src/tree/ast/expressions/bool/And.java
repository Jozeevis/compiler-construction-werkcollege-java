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
		if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant && ((BoolConstant)right).constant);
		} else if (left instanceof BoolConstant && ((BoolConstant)left).constant == false) {
			return new BoolConstant(false);
		} else if (right instanceof BoolConstant && ((BoolConstant)right).constant == false) {
			return new BoolConstant(false);
		}
		return this;
	}

	@Override
	public String getCode() {
		return "and";
	}

}
