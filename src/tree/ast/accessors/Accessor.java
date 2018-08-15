/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class Accessor {

	public abstract Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException, DeclarationException;

	/**
	 * The addCodeToStack for an accessor should create the code that:
	 *  Given an address of the correct object is on the stack transforms that into an address of the next object.
	 * @param counter TODO
	 */
	public abstract void addCodeToStack(List<String> stack, LabelCounter counter);
	
	/**
	 * The addCodetoStack for an accessor, that is used to get de assignment address.
	 * @param stack
	 * @param counter
	 */
	public abstract void addAssignmentCodeToStack(List<String> stack, LabelCounter counter);

}
