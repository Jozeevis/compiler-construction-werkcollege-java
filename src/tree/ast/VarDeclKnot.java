/**
 * 
 */
package tree.ast;

import grammar.Expression;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import lexer.TokenType;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a variable declaration/initialization.
 * @author Flip van Spaendonck
 */
public class VarDeclKnot extends ASyntaxKnot {
	/** The type of the variable **/
	public final Type type;
	/** The identifier of the variable **/
	public final String id;
	/** The TokenExpression that holds the value of the variable**/
	public final TokenExpression body;
	
	
	
	public VarDeclKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(null /*TODO remove this entirely*/, parent);
		
		type = Type.inferType((SyntaxKnot)oldKnot.children[0]);
		id = ((TokenIdentifier) oldKnot.children[1].reduceToToken()).getValue();
		body = (TokenExpression) oldKnot.children[3].reduceToToken();
	}

}
