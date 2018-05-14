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
	
	public BaseType(PrimitiveType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseType) {
			return ((BaseType) obj).type == type;
		}
		return false;
	}
	
}
