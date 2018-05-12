package tree.ast;

import lexer.PrimitiveType;
import lexer.TokenExpression;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.TreeProcessing;
import tree.ast.types.BaseType;

/**
 * An abstract syntax knot representing an if-(else) statement.
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class IfElseStmtKnot extends ASyntaxKnot implements ITypeCheckable{
	
	/** The TokenExpression that denotes the boolean expression that is checked in the if **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body if the check expression is true **/
	public final SyntaxNode ifBody;
	/** The TokenExpression that denotes the function body if the check expression is false **/
	public final SyntaxNode elseBody;
	
	public IfElseStmtKnot(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) throws Exception {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		ifBody = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[1]).root;
		// Check if there is an else to this if-statement
		if (oldKnot.children.length > 2) {
			elseBody = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[2]).root;
		}
		else {
			elseBody = null;
		}
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		if (!check.type.equals(new BaseType(PrimitiveType.PRIMTYPE_BOOL)))
			return false;
		return check.expression.checkTypes(domain);
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		if (elseBody != null)
			return new SyntaxNode[] { ifBody, elseBody};
		return new SyntaxNode[] {ifBody};
	}

}
