/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class Accessor {

	public abstract Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException, DeclarationException;

	public abstract void addCodeToStack(List<String> stack);

}
