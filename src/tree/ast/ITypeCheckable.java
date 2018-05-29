/**
 * 
 */
package tree.ast;

import processing.TypeException;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public interface ITypeCheckable {

	public boolean checkTypes(IDDeclarationBlock domain);
}
