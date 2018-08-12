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
	
}
