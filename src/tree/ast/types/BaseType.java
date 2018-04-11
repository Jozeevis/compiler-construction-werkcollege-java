/**
 * 
 */
package tree.ast.types;

import lexer.PrimitiveType;

/**
 * @author Flip van Spaendonck
 *
 */
public class BaseType extends Type {
	
	private final PrimitiveType type;
	
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
