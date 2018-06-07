/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.ast.IDDeclarationBlock;
import tree.ast.types.CustomType;
import tree.ast.types.StructType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructVarAccessor extends Accessor {

	public final String accessor;

	private int offset;

	public StructVarAccessor(String id) {
		accessor = id;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException, DeclarationException {
		if (suppliedType instanceof CustomType) {
			for (int i = domain.block.length - 1; i >= 0; i--) {
				IDDeclaration declaration = domain.block[i];
				if (declaration.id.equals(((CustomType) suppliedType).typeName)) {
					suppliedType = declaration.type;
					break;
				}
			}
		}
		if (suppliedType instanceof StructType) {
			for (int i = 0; i < ((StructType) suppliedType).declarations.block.length; i++) {
				if (((StructType) suppliedType).declarations.block[i].id.equals(accessor)) {
					offset = i;
					return ((StructType) suppliedType).declarations.block[i].type;
				}
			}
			throw new DeclarationException(
					((StructType) suppliedType).structName + " does not have a variable with id: " + accessor + ".");
		}
		throw new TypeException(
				"Left supplied type was of type: " + suppliedType + ", while a StructType was expected.");
	}
	
	@Override
	public void addCodeToStack(List<String> stack) {
		stack.add("ldh " + -1 * offset);
	}

}
