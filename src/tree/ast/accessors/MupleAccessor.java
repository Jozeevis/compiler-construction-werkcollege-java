/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.types.MupleType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class MupleAccessor extends Accessor {

	public final int index;

	public MupleAccessor(int index) {
		this.index = index;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException, DeclarationException {
		if (suppliedType instanceof MupleType) {
			if (index < ((MupleType) suppliedType).types.length | index < 0) {
				return ((MupleType) suppliedType).types[index];
			} else
				throw new TypeException("Left supplied expression is a muple with "
						+ ((MupleType) suppliedType).types.length + " members, " + index + " would overshoot it.");
		} else
			throw new TypeException(
					"Left supplied type was of type: " + suppliedType + ", while a MupleType was expected.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.ast.accessors.Accessor#addCodeToStack(java.util.List)
	 */
	@Override
	public void addCodeToStack(List<String> stack) {
		stack.add("ldh "+ -1*index);
	}

}
