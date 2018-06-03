/**
 * 
 */
package tree.ast.types;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.TupleExp;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent a tuple of two possibly different types.
 * @author Flip van Spaendonck
 */
public class TupleType extends Type {

	public final Type leftType;
	public final Type rightType;
	
	public TupleType(Type left, Type right) {
		leftType = left;
		rightType = right;
	}
	
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof TupleType) {
			return ((TupleType)t).leftType.matches(leftType) & ((TupleType)t).rightType.matches(rightType);
		}
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return new TupleExp(leftType.getNullValue(), rightType.getNullValue());
	}
}
