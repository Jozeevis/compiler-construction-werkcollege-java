package tree.ast.types.specials;

import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * A Type object used to represent the type of a function.
 * @author Lars Kuijpers, Flip van Spaendonck
 */
public class FunctionType extends Type {

	public final Type[] inputTypes;	// Types on the left side of the arrow (can be empty)
	public final Type returnType;	// Type on the right side of the arrow (can be null)

	public FunctionType(Type[] left, Type returnType) {
		inputTypes = left;
		this.returnType = returnType;
	}

	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof FunctionType) {
			if (!returnType.matches(((FunctionType) t).returnType))
				return false;
			if (inputTypes.length != ((FunctionType)t).inputTypes.length)
				return false;
			for(int i=0; i<inputTypes.length; i++) {
				if (!inputTypes[i].matches(((FunctionType) t).inputTypes[i]))
					return false;
			}
			return true;
		} else
			return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return null;
	}
}