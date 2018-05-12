/**
 * 
 */
package tree.ast;

import java.util.LinkedList;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a variable declaration/initialization.
 * @author Flip van Spaendonck
 */
public class VarDeclNode extends ASyntaxKnot implements IDeclarable, ITypeCheckable {
	/** The type of the variable **/
	public final Type type;
	/** The identifier of the variable **/
	public final String id;
	/** The TokenExpression that holds the value of the variable**/
	public final TokenExpression body;
	
	public VarDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		
		type = Type.inferType((SyntaxExpressionKnot)oldKnot.children[0]);
		id = ((TokenIdentifier) oldKnot.children[1].reduceToToken()).getValue();
		body = (TokenExpression) oldKnot.children[3].reduceToToken();
	}



	@Override
	public IDDeclaration getDeclaration() {
		return new IDDeclaration(type, id);
	}



	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return body.expression.checkTypes(domain);
	}



	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}

}
