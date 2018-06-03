/**
 * 
 */
package tree.ast;

import java.util.List;

import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.FunCall;
import tree.ast.types.specials.VoidType;

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
		this.funCall = new FunCall((SyntaxExpressionKnot) oldKnot.children[0]);
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		try {
			return (funCall.checkTypes(domain).equals(VoidType.instance));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		funCall.addCodeToStack(stack, counter);
	}
	
}
