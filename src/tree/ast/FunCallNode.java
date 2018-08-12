/**
 * 
 */
package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.IDDeclarationBlock.Scope;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.FunCall;
import tree.ast.expressions.IllegalThisException;
import tree.ast.types.specials.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCallNode extends ASyntaxKnot {

	
	public final FunCall funCall;
	private boolean isVoid = false;
	
	/**
	 * @param frontier
	 * @throws IllegalThisException 
	 */
	public FunCallNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws IllegalThisException {
		super(frontier);
		SyntaxKnot funCallKnot = ((SyntaxKnot)oldKnot.children[0]);
		String id = ((TokenIdentifier)(funCallKnot).children[0].reduceToToken()).value;
		BaseExpr[] arguments;
		
		if (funCallKnot.children.length == 3) {
			arguments = new BaseExpr[0];
		} else {
			SyntaxExpressionKnot currentArgument = (SyntaxExpressionKnot) funCallKnot.children[2];
			List<BaseExpr> args = new LinkedList<>();
			while (currentArgument.children.length == 3) {
				args.add(((TokenExpression)((SyntaxExpressionKnot) currentArgument.children[0]).reduceToToken()).expression);
				currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
			}
			args.add(((TokenExpression)((SyntaxExpressionKnot) currentArgument.children[0]).reduceToToken()).expression);
			arguments = new BaseExpr[args.size()];
			for(int i=0; i< arguments.length; i++) {
				arguments[i] = args.get(i);
			}
		}
		funCall = new FunCall(id, arguments);
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		isVoid = (funCall.checkTypes(domain) instanceof VoidType);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		funCall.addCodeToStack(stack, counter);
		if (!isVoid)
			stack.add("ajs -1\n");
	}

	@Override
	public boolean alwaysReturns() {
		return false;
	}
	
}
