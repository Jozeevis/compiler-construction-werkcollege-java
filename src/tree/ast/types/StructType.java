/**
 * 
 */
package tree.ast.types;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.structs.NullExpr;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent custom types.
 * @author Flip van Spaendonck
 */
public class StructType extends Type {

	public final String typeName;
	
	public StructType(String name) {
		typeName = name;
	}
	
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof StructType) {
			return ((StructType)t).typeName.equals(typeName);
		}
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return NullExpr.instanceOf;
	}
}
