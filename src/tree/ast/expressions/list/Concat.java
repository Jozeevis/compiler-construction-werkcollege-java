/**
 * 
 */
package tree.ast.expressions.list;

import java.util.List;

import org.w3c.dom.css.Counter;

import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;

/**
 * @author Flip van Spaendonck
 *
 */
public class Concat extends TwoArg {

	public Concat(BaseExpr left, BaseExpr right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		return this;
	}


	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("stmh 2");
	}

}
