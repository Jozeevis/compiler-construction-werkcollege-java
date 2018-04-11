/**
 * 
 */
package tree.ast;

import grammar.Expression;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class VarDeclKnot extends ASyntaxKnot {

	public final Type type;
	public final String id;
	public final TokenExpression body;
	
	
	
	public VarDeclKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(null /*TODO remove this entirely*/, parent);
		
		type = Type.inferType((SyntaxKnot)oldKnot.children[0]);
		id = ((TokenIdentifier) oldKnot.children[1].reduceToToken()).getValue();
		body = (TokenExpression) oldKnot.children[3].reduceToToken();
		
	}

}
