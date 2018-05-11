/**
 * 
 */
package tree.ast;

import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.FunCall;
import tree.ast.types.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCallNode extends ASyntaxKnot implements ITypeCheckable{

	
	public final FunCall funCall;
	
	/**
	 * @param frontier
	 */
	public FunCallNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) {
		super(frontier);
		this.funCall = new FunCall((SyntaxExpressionKnot) oldKnot.children[0], new VoidType());
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return funCall.checkTypes(domain);
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}
	
}
