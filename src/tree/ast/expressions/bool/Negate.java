/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import tree.ast.LabelCounter;
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
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		val.addCodeToStack(stack, counter);
		stack.add("not");
	}

}
