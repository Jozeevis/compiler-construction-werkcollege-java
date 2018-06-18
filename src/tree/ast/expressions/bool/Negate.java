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
import tree.ast.expressions.OneArg;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

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

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType;
		if (!(expressionType = val.checkTypes(domain)).equals(BaseType.instanceInt))
			throw new TypeException("Expression was of type: "+expressionType+" while type Bool was expected.");
		return BaseType.instanceBool;
	}
	
}
