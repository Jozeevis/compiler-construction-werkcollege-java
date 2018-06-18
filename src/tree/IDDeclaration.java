/**
 * 
 */
package tree;

import tree.IDDeclarationBlock.Scope;
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
	public final int offset;
	public final Scope scope;

	public IDDeclaration(String id, Type type, Scope scope, int offset) {
		this.type = type;
		this.id = id;
		this.offset = offset;
		this.scope = scope;
	}

}
