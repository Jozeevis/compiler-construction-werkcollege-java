/**
 * 
 */
package tree.ast.expressions.num;

import java.util.List;

import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;

/**
 * @author Flip van Spaendonck
 *
 */
public class Negative extends OneArg {

	/**
	 * @param val
	 */
	public Negative(BaseExpr val) {
		super(val);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		val.optimize();
		if (val instanceof NumConstant) {
			return new NumConstant(-((NumConstant)val).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		val.addCodeToStack(stack, counter);
		stack.add("neg");
	}

}
