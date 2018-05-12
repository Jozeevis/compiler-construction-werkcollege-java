/**
 * 
 */
package tree.ast.expressions;

import java.util.List;
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
		left.optimize();
		right.optimize();
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack) {
		left.addCodeToStack(stack);
		stack.add("sth");
		right.addCodeToStack(stack);
		stack.add("sth");
		stack.add("stmh 2");
		
	}

}
