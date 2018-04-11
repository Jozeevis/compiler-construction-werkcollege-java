/**
 * 
 */
package tree.ast.types;

/**
 * @author Flip van Spaendonck
 *
 */
public class CustomType extends Type {

	public final String typeName;
	
	public CustomType(String name) {
		typeName = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomType) {
			return ((CustomType)obj).typeName.equals(typeName);
		}
		return false;
	}
}
