/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import lexer.PrimitiveType;
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
public class And extends TwoArg {

	public And(BaseExpr left, BaseExpr right) {
		super(left, right);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		left.optimize();
		right.optimize();
		// false && x = false
		if (left instanceof BoolConstant && ((BoolConstant)left).constant == false) {
			return new BoolConstant(false);
		} 
		// x && false = false
		else if (right instanceof BoolConstant && ((BoolConstant)right).constant == false) {
			return new BoolConstant(false);
		}
		// x && y = x&&y if x,y are constants
		else if (left instanceof BoolConstant & right instanceof BoolConstant) {
			return new BoolConstant(((BoolConstant)left).constant && ((BoolConstant)right).constant);
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("and");
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
