package tree.ast;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.TreeProcessing;
import tree.ast.types.BaseType;

/**
 * An abstract syntax knot representing a while statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class WhileStmtKnot extends ASyntaxKnot implements ITypeCheckable {
	/** The TokenExpression that denotes the boolean expression that is checked in the while **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;
	
	public WhileStmtKnot(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) throws Exception {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[1]).root;
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		if (!check.type.equals(new BaseType(PrimitiveType.PRIMTYPE_BOOL)))
			return false;
		return check.expression.checkTypes(domain);
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[] {body};
	}

}
