/**
 * 
 */
package tree.ast.expressions;

import java.util.Map;

/**
 * @author Flip van Spaendonck
 *
 */
public class TupleExp extends TwoArg {

	public TupleExp(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		// TODO Auto-generated method stub
		return null;
	}

}
