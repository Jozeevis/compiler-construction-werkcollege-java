package tree.ast.expressions.num;

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
 *
 * @author Flip van Spaendonck and Lars Kuijpers
 */
public class Divide extends TwoArg {

    public Divide(BaseExpr left, BaseExpr right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " / " + right + ")";
    }

    @Override
    public BaseExpr optimize() {
        left = left.optimize();
        right = right.optimize();
        // x / 0 = undefined
        if (right instanceof NumConstant && ((NumConstant)right).constant == 0) {
            //throw new ArithmeticException("Can't divide by zero");
            return this;
        } 
        // 0 / x = 0
        else if (left instanceof NumConstant && ((NumConstant)left).constant == 0) {
        	return new NumConstant(0);
        } 
        // x / 1 = x
        else if (right instanceof NumConstant && ((NumConstant)right).constant == 1) {
            return right;
        } 
        // x / y = x/y if x,y are constants
        else if (left instanceof NumConstant & right instanceof NumConstant) {
            return new NumConstant( ((NumConstant)left).constant % ((NumConstant)right).constant);
        } 
        else {
        	return this;
        }
    }

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		left.addCodeToStack(stack, counter);
		right.addCodeToStack(stack, counter);
		stack.add("div");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type leftType = left.checkTypes(domain);
		if (!(leftType.equals(BaseType.instanceInt) || leftType.equals(BaseType.instanceChar)))
			throw new TypeException("Left expression was of type: "+leftType+" while type Boolean or Character was expected.");
		Type rightType;
		if (!(rightType = right.checkTypes(domain)).equals(leftType))
			throw new TypeException("Right expression was of type: "+rightType+" while type "+leftType+" was expected.");
		return leftType;
	}
}
