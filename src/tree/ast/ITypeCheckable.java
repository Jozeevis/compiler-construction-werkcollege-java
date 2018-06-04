/**
 * 
 */
package tree.ast;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public interface ITypeCheckable {

	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException;
}
