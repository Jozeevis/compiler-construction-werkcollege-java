package tree.ast;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import tree.IDDeclaration;
import tree.IKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.BaseType;

/**
 * An abstract syntax knot representing an if-(else) statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class IfElseStmtKnot extends ASyntaxNode implements IKnot, ITypeCheckable{
	
	/** The TokenExpression that denotes the boolean expression that is checked in the if **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body if the check expression is true **/
	public final SyntaxNode ifBody;
	/** The TokenExpression that denotes the function body if the check expression is false **/
	public final SyntaxNode elseBody;
	
	public IfElseStmtKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		ifBody = oldKnot.children[1];
		// Check if there is an else to this if-statement
		if (oldKnot.children.length > 2) {
			elseBody = oldKnot.children[2];
		}
		else {
			elseBody = null;
		}
	}

	@Override
	public SyntaxNode[] getChildren() {
		return new SyntaxNode[] { ifBody, elseBody};
	}

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		if (!check.type.equals(new BaseType(PrimitiveType.PRIMTYPE_BOOL)))
			return false;
		return check.expression.checkTypes(domain);
	}

}
