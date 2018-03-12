/**
 * 
 */
package lexer;

import java.util.List;

/**
 * @author Flip van Spaendonck
 *
 */
public class TokenExpression extends Token {

	private Token[] tokens;
	
	public TokenExpression(List<Token> list) {
		super(TokenType.TOK_EXP);
		tokens = (Token[]) list.toArray();
		/*TODO this token should actually check whether the tokens creating this expression 
		 * form a valid expression and check for optimization */
		
	}

}
