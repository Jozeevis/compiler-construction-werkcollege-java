/**
 * 
 */
package tree.ast.types;

/**
 * A Type object used to represent a tuple of two possibly different types.
 * @author Flip van Spaendonck
 */
public class TupleType extends Type {

	public final Type leftType;
	public final Type rightType;
	
	public TupleType(Type left, Type right) {
		leftType = left;
		rightType = right;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TupleType) {
			return ((TupleType)obj).leftType == leftType & ((TupleType)obj).rightType == rightType;
		}
		return false;
	}
}
