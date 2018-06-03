package tree.ast.types.specials;

import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

public class VoidType extends Type {
	
	public static final VoidType instance = new VoidType();
	
	private VoidType() {};

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof VoidType);
	}

	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof VoidType)
			return true;
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return null;
	}

}
