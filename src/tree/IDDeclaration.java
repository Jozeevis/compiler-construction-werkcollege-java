/**
 * 
 */
package tree;

import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class IDDeclaration {

	public final Type type;
	
	public final String id;
	
	
	/**
	 * 
	 */
	public IDDeclaration(Type type, String id) {
		this.type = type;
		this.id = id;
	}

}
