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
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class Or extends TwoArg {

	public Or(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		// true || x = true
		if (left instanceof BoolConstant && ((BoolConstant)left).constant == true) {
			return new BoolConstant(true);
		} 
		// x || true = x
		else if (right instanceof BoolConstant && ((BoolConstant)right).constant == true) {
			return new BoolConstant(true);
		}
		// x || y = x||y if x,y are constants
		else if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant || ((BoolConstant)right).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("or");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType;
		if (!(expressionType = left.checkTypes(domain)).equals(BaseType.instanceBool))
			throw new TypeException("Left expression was of type: "+expressionType+" while type Boolean was expected.");
		if (!(expressionType = right.checkTypes(domain)).equals(BaseType.instanceBool))
			throw new TypeException("Right expression was of type: "+expressionType+" while type Boolean was expected.");
		return BaseType.instanceBool;
	}


}
