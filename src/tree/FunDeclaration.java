/**
 * 
 */
package tree;

import tree.IDDeclarationBlock.Scope;
import tree.ast.types.StructType;
import tree.ast.types.Type;
import tree.ast.types.specials.FunctionType;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunDeclaration{

	/** The type of the variable or function. **/
	public final FunctionType type;
	/** The identifier used for the variable or function. **/
	public final String id;
	public final String branchAddress;

	public FunDeclaration(String id, FunctionType type, String branchAddress) {
		this.type = type;
		this.id = id;
		this.branchAddress = branchAddress;
	}

}
