/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import tree.ast.IDDeclarationBlock;

/**
 * @author Flip van Spaendonck
 *
 */
public class SetExp extends BaseExpr {
	
	
	

	/* (non-Javadoc)
	 * @see tree.ast.ITypeCheckable#checkTypes(tree.ast.IDDeclarationBlock)
	 */
	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#addCodeToStack(java.util.List)
	 */
	@Override
	public void addCodeToStack(List<String> stack) {
		// TODO Auto-generated method stub

	}

}
