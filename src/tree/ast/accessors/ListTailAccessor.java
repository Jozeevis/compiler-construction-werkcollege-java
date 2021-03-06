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
public class ListTailAccessor extends Accessor{

	public final static ListTailAccessor instance = new ListTailAccessor();

	@Override
	public Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException {
		if (suppliedType instanceof ListType) {
			return suppliedType;
		} else
			throw new TypeException("Left supplied type was of type: "+suppliedType+ ", while a ListType was expected.");
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldh 0\n");
	}

	@Override
	public void addAssignmentCodeToStack(List<String> stack, LabelCounter counter) {
		//Given that the address of the current list is on the stack
		//The address of the list and its tail are the same address so nothing needs to be done
		//Now the address that is used to store the tail variable, is on top of the stack.
	}
	
}
