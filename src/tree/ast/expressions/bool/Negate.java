/**
 * 
 */
package tree.ast.expressions.bool;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;

/**
 * @author Flip van Spaendonck
 *
 */
public class Negate extends OneArg {

	public Negate(BaseExpr val) {
		super(val);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		val.optimize();
		if (val instanceof BoolConstant) {
			return new BoolConstant(!((BoolConstant)val).constant);
		}
		return this;
	}

	@Override
	public String getCode() {
		return "not";
	}

}
