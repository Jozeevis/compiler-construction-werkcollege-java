/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import tree.IDDeclaration;

/**
 * @author Flip van Spaendonck
 *
 */
public class EmptyList extends NoArg {

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		return true;
	}

}
