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
 * An abstract syntax knot representing a while statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class WhileStmtKnot extends ASyntaxNode implements IKnot, ITypeCheckable {
	/** The TokenExpression that denotes the boolean expression that is checked in the while **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;
	
	public WhileStmtKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		body = oldKnot.children[1];
	}

	@Override
	public SyntaxNode[] getChildren() {
		return new SyntaxNode[] {body};
	}

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		if (!check.type.equals(new BaseType(PrimitiveType.PRIMTYPE_BOOL)))
			return false;
		return check.expression.checkTypes(domain);
	}

}
