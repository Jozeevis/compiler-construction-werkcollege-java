/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.ListType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class ListHeadAccessor extends Accessor{

	public final static ListHeadAccessor instance = new ListHeadAccessor();

	@Override
	public Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException {
		if (suppliedType instanceof ListType) {
			return ((ListType) suppliedType).listedType;
		} else
			throw new TypeException("Left supplied type was of type: "+suppliedType+ ", while a ListType was expected.");
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldh -1\n");
	}

	@Override
	public void addAssignmentCodeToStack(List<String> stack, LabelCounter counter) {
		//Given that the address of the current list is on the stack
		stack.add("ldc 1 \n");
		stack.add("sub \n");
		//Now the address that is used to store the head variable, is on top of the stack.
	}
	
}
