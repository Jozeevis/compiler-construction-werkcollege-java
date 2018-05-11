/**
 * 
 */
package tree.ast.expressions;

import tree.ast.IDDeclarationBlock;

/**
 * @author Flip van Spaendonck
 *
 */
public class EmptyList extends NoArg {

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return true;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
