/**
 * 
 */
package lexer;

import java.util.List;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class TokenField extends Token {

	protected TokenField(TokenType tokType) {
		super(tokType);
	}

	public abstract void addCodeToStack(List<String> stack);
	

}
