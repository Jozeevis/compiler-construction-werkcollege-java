/**
 * 
 */
package tree.ast.types;

import lexer.PrimitiveType;

/**
 * A Type object used to represent basic primitive types.
 * @author Flip van Spaendonck
 */
public class BaseType extends Type {
	
	public final PrimitiveType type;
	
	public static final BaseType instanceBool = new BaseType(PrimitiveType.PRIMTYPE_BOOL);
	public static final BaseType instanceChar = new BaseType(PrimitiveType.PRIMTYPE_CHAR);
	public static final BaseType instanceInt = new BaseType(PrimitiveType.PRIMTYPE_INT);
	
	private BaseType(PrimitiveType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseType) {
			return ((BaseType) obj).type == type;
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
	
}
