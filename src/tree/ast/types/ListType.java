/**
 * 
 */
package tree.ast.types;

/**
 * @author Flip van Spaendonck
 *
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
