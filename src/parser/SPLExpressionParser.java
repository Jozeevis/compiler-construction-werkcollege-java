/**
 * 
 */
package parser;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenType;
import tree.ast.expressions.IllegalThisException;

/**
 * @author Flip van Spaendonck
 */
public class SPLExpressionParser {

	public static final void packExpressions(List<Token> tokens) throws ParsingException, IllegalThisException {
		loop: for (int i = 0; i < tokens.size(); i++) {
			TokenType current = tokens.get(i).getTokenType();
			if (current == TokenType.TOK_ASS | current == TokenType.TOK_KW_RETURN | current == TokenType.TOK_KW_PRINT) {
				i++;
				int end = i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_EOS)
						end++;
					//In case of a return;
					if (end == i)
						continue loop;
					System.out.println("t:"+tokens.subList(i, end));
					TokenExpression expressionToken = new TokenExpression(new LinkedList<>(tokens.subList(i, end)));
					while (end - i > 0) {
						System.out.println("Removing: "+tokens.remove(i));
						end--;
					}
					tokens.add(i, expressionToken);
					i++;
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					throw new ParsingException();
				}
			} else if (current == TokenType.TOK_KW_IF | current == TokenType.TOK_KW_WHILE) {
				if (tokens.get(++i).getTokenType() != TokenType.TOK_BRACK_OPEN)
					throw new ParsingException();
				i++;
				int end = i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_BLOCK_OPEN)
						end++;
					end--;
					TokenExpression expressionToken = new TokenExpression(new LinkedList<>(tokens.subList(i, end)));
					while (end - i > 0) {
						tokens.remove(i);
						end--;
					}
					tokens.add(i, expressionToken);
					i += 2;
				} catch (IndexOutOfBoundsException e) {
					throw new ParsingException();
				}
			//Parsing expressions in funcall
			} else if (current == TokenType.TOK_IDENTIFIER
					&& tokens.get(i+1).getTokenType() == TokenType.TOK_BRACK_OPEN) {
				i++;
				int end = ++i;
				try {
					while (tokens.get(end).getTokenType() != TokenType.TOK_EOS) {
						if (tokens.get(end).getTokenType() == TokenType.TOK_BLOCK_OPEN | tokens.get(end).getTokenType() == TokenType.TOK_FUNTYPE)
							continue loop;
						end++;
					}
					if (tokens.get(--end).getTokenType() != TokenType.TOK_BRACK_CLOSE)
						throw new ParsingException();
					// Now its time to parse the possible multiple arguments into seperate
					// expressions.
					List<Token> arguments = new LinkedList<>(tokens.subList(i, end));
					while (end - i > 0) {
						tokens.remove(i);
						end--;
					}
					int brackCount = 0;
					List<Token> argument = new LinkedList<>();
					System.out.println("These tokens are the arguments of this funcall: "+arguments);
					if (arguments.isEmpty()) {
						continue loop;
					}
					while (!arguments.isEmpty()) {
						Token token = arguments.remove(0);
						if ((token.getTokenType() == TokenType.TOK_COMMA )& brackCount == 0) {
							tokens.add(i++, new TokenExpression(argument));
							tokens.add(i++, token);
							arguments.clear();
						} else if (token.getTokenType() == TokenType.TOK_BRACK_OPEN) {
							brackCount++;
						} else if (token.getTokenType() == TokenType.TOK_BRACK_CLOSE) {
							brackCount--;
						} else
							argument.add(argument.size(), token);
					}
					if (brackCount != 0) {
						throw new ParsingException(); // There is a problem with the amount of brackets because of this
					}								// the current argument is invalid.
					tokens.add(i++, new TokenExpression(argument));
				} catch (IndexOutOfBoundsException e) {
					throw new ParsingException();
				}
			} // if else end
		} // for loop end
	} // Method end
}
