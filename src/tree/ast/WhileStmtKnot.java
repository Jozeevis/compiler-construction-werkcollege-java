package tree.ast;

import lexer.TokenExpression;
import tree.SyntaxKnot;

/**
 * An abstract syntax knot representing a while statement.
 * @author Lars Kuijpers
 */
public class WhileStmtKnot extends ASyntaxNode {
	/** The TokenExpression that denotes the boolean expression that is checked in the while **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body **/
	public final TokenExpression body;
	
	public WhileStmtKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		body = (TokenExpression) oldKnot.children[1].reduceToToken();
	}

}
