/**
 * 
 */
package tree.ast;

import java.util.List;

import tree.IDDeclaration;

/**
 * @author Flip van Spaendonck
 *
 */
public interface ITypeCheckable {

	public boolean checkTypes(List<IDDeclaration> domain);
}
