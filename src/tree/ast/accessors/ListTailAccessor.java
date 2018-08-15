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
	
}
