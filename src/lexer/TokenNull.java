/**
 * 
 */
package lexer;

/**
 * @author Flip van Spaendonck
 *
 */
public class TokenNull extends Token {

	public final static TokenNull instanceOf = new TokenNull();
	
	private TokenNull() {
		super(TokenType.TOK_KW_NULL);
	}

}
