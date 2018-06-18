/**
 * 
 */
package tree.ast.types;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.structs.NullExpr;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent custom types. This type is only used as a way
 * of passing through the name of a custom type, the handling of the actual type
 * should be done at the receiving end, by looking up the StructType in the
 * IDDeclarationBlock.
 * 
 * @author Flip van Spaendonck
 */
public class CustomType extends Type {

	public final String typeName;

	public CustomType(String name) {
		typeName = name;
	}

	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof CustomType) {
			return ((CustomType) t).typeName.equals(typeName);
		}
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return NullExpr.instanceOf;
	}
}
