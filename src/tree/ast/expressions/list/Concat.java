/**
 * 
 */
package tree.ast.expressions.list;

import java.util.List;

import org.w3c.dom.css.Counter;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;
import tree.ast.types.ListType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class Concat extends TwoArg {

	public Concat(BaseExpr left, BaseExpr right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
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

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type listType;
		if ((listType = right.checkTypes(domain)) instanceof ListType) {
			Type leftType;
			if (!(leftType = left.checkTypes(domain)).equals(((ListType) listType).listedType))
				throw new TypeException("Left expression was of type: " + leftType + " while Type "
						+ ((ListType) listType).listedType + " was expected.");
		} else
			throw new TypeException(
					"Right expression was of type: " + listType + " while Type ListType[_] was expected.");
		return listType;
	}

}
