/**
 * 
 */
package tree.ast.types;

import lexer.PrimitiveType;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.bool.BoolConstant;
import tree.ast.expressions.num.CharConstant;
import tree.ast.expressions.num.NumConstant;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent basic primitive types.
 * @author Flip van Spaendonck
 */
public class BaseType extends Type {
	
	public final PrimitiveType type;
	private final BaseExpr nullValue;
	
	public static final BaseType instanceBool = new BaseType(PrimitiveType.PRIMTYPE_BOOL, new BoolConstant(false));
	public static final BaseType instanceChar = new BaseType(PrimitiveType.PRIMTYPE_CHAR, new CharConstant('\u0000'));
	public static final BaseType instanceInt = new BaseType(PrimitiveType.PRIMTYPE_INT, new NumConstant(0));
	
	private BaseType(PrimitiveType type, BaseExpr nullValue) {
		this.type = type;
		this.nullValue = nullValue;
	}
	
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof BaseType) {
			return ((BaseType) t).type == type;
		}
		return false;
	}

	public static BaseType instanceOf(PrimitiveType primType) {
		switch(primType) {
		case PRIMTYPE_BOOL:
			return instanceBool;
		case PRIMTYPE_CHAR:
			return instanceChar;
		case PRIMTYPE_INT:
			return instanceInt;
		}
		return null;
	}

	@Override
	public BaseExpr getNullValue() {
		return nullValue;
	}
	
	@Override
	public String toString() {
		return type.name();
	}
	
}
