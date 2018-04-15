/**
 * 
 */
package tree.ast.expressions;

import tree.SyntaxKnot;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCall extends BaseExpr {
	
	public final BaseExpr[] arguments;

	public FunCall(SyntaxKnot funcall) {
		//TODO: correctly translate funcalls
		arguments = new BaseExpr[0];
	}
	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		for(BaseExpr argument : arguments)
			argument.optimize();
		return this;
	}

}
