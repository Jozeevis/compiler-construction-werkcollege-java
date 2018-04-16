/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import tree.IDDeclaration;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class AssignmentNode extends SyntaxNode implements ITypeCheckable{

	public final String id;
	
	public final TokenExpression expression;
	
	public AssignmentNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		
		id = ((TokenIdentifier)oldKnot.children[0].reduceToToken()).value;
		expression = (TokenExpression)oldKnot.children[0].reduceToToken();
	}

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		Type varType = null;
		for (IDDeclaration declaration : domain) {
			if (declaration.id.equals(id)) {
				varType = declaration.type;
				break;
			}
		}
		if (varType == null)
			return false;
		return varType.equals(expression.type);
	}

}
