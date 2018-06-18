/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TwoArg;
import tree.ast.expressions.num.NumConstant;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class LargerEq extends TwoArg {

	public LargerEq(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		if (left instanceof NumConstant & right instanceof NumConstant) {
			return new BoolConstant(((NumConstant)left).constant >= ((NumConstant)right).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("ge");
	}
	
	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType;
		if (!(expressionType = left.checkTypes(domain)).equals(BaseType.instanceInt))
			throw new TypeException("Left expression was of type: "+expressionType+" while type Integer was expected.");
		if (!(expressionType = right.checkTypes(domain)).equals(BaseType.instanceInt))
			throw new TypeException("Right expression was of type: "+expressionType+" while type Integer was expected.");
		return BaseType.instanceBool;
	}


}
