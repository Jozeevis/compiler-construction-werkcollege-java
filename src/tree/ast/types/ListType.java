/**
 * 
 */
package tree.ast.types;

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
}
