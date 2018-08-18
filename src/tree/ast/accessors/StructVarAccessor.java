/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
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
			suppliedType = domain.findStructDeclaration(((CustomType)suppliedType).typeName).structType;
		}
		if (suppliedType instanceof StructType) {
			try {
				IDDeclaration attributeDeclaration = ((StructType)suppliedType).findIDDeclaration(accessor);
				offset = attributeDeclaration.offset;
				return attributeDeclaration.type;
			} catch (DeclarationException e) {
				throw new DeclarationException(
						((StructType) suppliedType).structName + " does not have a variable with id: " + accessor + ".");
			}
		}
		throw new TypeException(
				"Left supplied type was of type: " + suppliedType + ", while a StructType was expected.");
	}
	
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldh " + -1 * offset + "\n");
	}

	@Override
	public void addAssignmentCodeToStack(List<String> stack, LabelCounter counter) {
		//Given that the address of the current list is on the stack
		stack.add("ldc "+offset+" \n");
		stack.add("sub \n");
		//Now the address that is used to store the head variable, is on top of the stack.
	}

}
