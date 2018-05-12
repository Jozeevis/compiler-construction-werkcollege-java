/**
 * 
 */
package tree;

import tree.ast.types.Type;

/**
 * A String, Type tuple storing what type the id is.
 * 
 * @author Flip van Spaendonck
 */
public class IDDeclaration {

	/** The type of the variable or function. **/
	public final Type type;
	/** The identifier used for the variable or function. **/
	public final String id;

	public IDDeclaration(Type type, String id) {
		this.type = type;
		this.id = id;
	}

}
