package tree.ast.expressions;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.TupleType;
import tree.ast.types.Type;

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
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		stack.add("sth");
		right.addCodeToStack(stack, counter);
		stack.add("sth");
		stack.add("stmh 2");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return new TupleType(left.checkTypes(domain), right.checkTypes(domain));
	}

}
