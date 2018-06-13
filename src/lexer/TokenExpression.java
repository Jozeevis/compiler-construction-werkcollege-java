/**
 * 
 */
package lexer;

import java.util.List;

import grammar.EXP;
import parser.Parser;
import tree.SyntaxExpressionKnot;
import tree.SyntaxTree;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class TokenExpression extends Token {

	public final BaseExpr expression;
	
	public TokenExpression(List<Token> list) {
		super(TokenType.TOK_EXP);
		System.out.println("test2:" +list);
		SyntaxTree tree = Parser.convertTokenTraces(Parser.parseCode(EXP.INSTANCE, list));
		BaseExpr tempExpression = BaseExpr.convertToExpr((SyntaxExpressionKnot) tree.root);
		expression = tempExpression.optimize();
		
	}
}
