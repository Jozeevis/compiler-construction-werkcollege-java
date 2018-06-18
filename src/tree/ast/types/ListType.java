/**
 * 
 */
package tree.ast.types;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.list.EmptyList;
import tree.ast.types.specials.WildType;

/**
 * A Type object used to represent a list of objects of one specific type.
 * @author Flip van Spaendonck
 */
public class ListType extends Type {
	
	public final Type listedType;
	
	public ListType(Type listedType) {
		this.listedType = listedType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ListType) {
			return ((ListType) obj).listedType == listedType;
		}
		return false;
	}

	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		if (t instanceof ListType) {
			return ((ListType) t).listedType == listedType;
		}
		return false;
	}

	@Override
	public BaseExpr getNullValue() {
		return EmptyList.instanceOf;
	}
}
