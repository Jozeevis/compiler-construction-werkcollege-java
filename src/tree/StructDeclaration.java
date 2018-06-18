/**
 * 
 */
package tree;

import java.util.List;

import tree.ast.types.StructType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructDeclaration {

	public final String id;
	public final String constructorBranchAddress;
	public final StructType structType;
	public final Type[] constructorArgumentTypes;
	
	public StructDeclaration(String id, StructType structType, Type[] cArgTypes, String constructorBranchAddress) {
		this.id = id;
		this.structType =structType;
		this.constructorBranchAddress = constructorBranchAddress;
		this.constructorArgumentTypes = cArgTypes;
	}

}
