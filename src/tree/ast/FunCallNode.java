/**
 * 
 */
package tree.ast;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
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
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		funCall.checkTypes(domain);
		return domain;
		
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
