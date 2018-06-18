/**
 * 
 */
package tree.ast.types;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.MupleExp;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent a tuple of two possibly different types.
 * @author Flip van Spaendonck
 */
public class MupleType extends Type {

	public final Type[] types;
	
	public MupleType(Type[] types) {
		this.types = types;
	}
	
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof MupleType) {
			MupleType otherMupleType = (MupleType) t;
			if (otherMupleType.types.length != types.length)
				return false;
			for(int i=0; i < types.length; i++) {
				if (!types[i].matches(otherMupleType.types[i]))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		BaseExpr[] nullValues = new BaseExpr[types.length];
		for(int i=0; i < types.length; i++) {
			nullValues[i] = types[i].getNullValue();
		}
		return new MupleExp(nullValues);
	}
}
