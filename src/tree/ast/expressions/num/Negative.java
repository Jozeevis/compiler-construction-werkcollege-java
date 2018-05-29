/**
 * 
 */
package tree.ast.expressions.num;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

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

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType = val.checkTypes(domain);
		if (!(expressionType.equals(BaseType.instanceInt) || expressionType.equals(BaseType.instanceChar)))
			throw new TypeException("Expression was of type: "+expressionType+" while type Bool was expected.");
		return expressionType;
	}
	
}
