/**
 * 
 */
package parser;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenType;

/**
 * @author Flip van Spaendonck
 */
public class SPLExpressionParser {

	public static final void packExpressions(List<Token> tokens) throws ParsingException {
		loop: for (int i = 0; i < tokens.size(); i++) {
			TokenType current = tokens.get(i).getTokenType();
			if (current == TokenType.TOK_ASS | current == TokenType.TOK_KW_RETURN) {
				i++;
				int end = i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_EOS)
						end++;
					TokenExpression expressionToken = new TokenExpression(tokens.subList(i, end));
					while (end - i > 0) {
						tokens.remove(i);
						end--;
					}
					tokens.add(i, expressionToken);
					i++;
				} catch (IndexOutOfBoundsException e) {
					throw new ParsingException();
				}
			} else if (current == TokenType.TOK_KW_IF | current == TokenType.TOK_KW_WHILE) {
				if (tokens.get(++i).getTokenType() != TokenType.TOK_BRACK_OPEN)
					throw new ParsingException();
				i++;
				int end = i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_BLOCK_CLOSE)
						end++;
					end--;
					TokenExpression expressionToken = new TokenExpression(tokens.subList(i, end));
					while (end - i > 0) {
						tokens.remove(i);
						end--;
					}
					tokens.add(i, expressionToken);
					i += 2;
				} catch (IndexOutOfBoundsException e) {
					throw new ParsingException();
				}
			} else if (current == TokenType.TOK_IDENTIFIER
					& tokens.get(++i).getTokenType() == TokenType.TOK_BRACK_OPEN) {
				int end = ++i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_EOS) {
						if (tokens.get(end).getTokenType() == TokenType.TOK_BLOCK_OPEN)
							continue loop;
						end++;
					}
					if (tokens.get(--end).getTokenType() != TokenType.TOK_BRACK_CLOSE)
						throw new ParsingException();
					// Now its time to parse the possible multiple arguments into seperate
					// expressions.
					List<Token> arguments = tokens.subList(i, end);
					while (end - i > 0) {
						tokens.remove(i);
						end--;
					}
					int brackCount = 0;
					List<Token> argument = new LinkedList<>();
					while (!arguments.isEmpty()) {
						Token token = arguments.remove(0);
						if (token.getTokenType() == TokenType.TOK_COMMA & brackCount == 0) {
							tokens.add(i++, new TokenExpression(argument));
							tokens.add(i++, token);
							arguments.clear();
						} else if (token.getTokenType() == TokenType.TOK_BRACK_OPEN) {
							brackCount++;
						} else if (token.getTokenType() == TokenType.TOK_BLOCK_CLOSE) {
							brackCount--;
						} else
							argument.add(argument.size(), token);
					}
					if (argument.size() != 0)
						throw new ParsingException(); // There is a problem with the amount of brackets because of this
														// the current argument is invalid.
				} catch (IndexOutOfBoundsException e) {
					throw new ParsingException();
				}
			} // if else end
		} // for loop end
	} // Method end
}
