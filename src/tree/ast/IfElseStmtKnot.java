package tree.ast;

import lexer.TokenExpression;
import tree.SyntaxKnot;

/**
 * An abstract syntax knot representing an if-(else) statement.
 * @author Lars Kuijpers
 */
public class IfElseStmtKnot extends ASyntaxNode {
	/** The TokenExpression that denotes the boolean expression that is checked in the if **/
	public final TokenExpression check;
	/** The TokenExpression that denotes the function body if the check expression is true **/
	public final TokenExpression ifBody;
	/** The TokenExpression that denotes the function body if the check expression is false **/
	public final TokenExpression elseBody;
	
	public IfElseStmtKnot(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		check = (TokenExpression) oldKnot.children[0].reduceToToken();
		ifBody = (TokenExpression) oldKnot.children[1].reduceToToken();
		// Check if there is an else to this if-statement
		if (oldKnot.children.length > 2) {
			elseBody = (TokenExpression) oldKnot.children[2].reduceToToken();
		}
		else {
			elseBody = null;
		}
	}

}
