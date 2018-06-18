/**
 * 
 */
package tree.ast;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.IDDeclarationBlock.Scope;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.FunCall;
import tree.ast.expressions.IllegalThisException;
import tree.ast.types.specials.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCallNode extends ASyntaxKnot {

	
	public final FunCall funCall;
	
	/**
	 * @param frontier
	 * @throws IllegalThisException 
	 */
	public FunCallNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws IllegalThisException {
		super(frontier);
		this.funCall = new FunCall((SyntaxExpressionKnot) oldKnot.children[0]);
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		funCall.checkTypes(domain);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		funCall.addCodeToStack(stack, counter);
	}
	
}
