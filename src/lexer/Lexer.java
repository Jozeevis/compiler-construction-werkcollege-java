package lexer;

import java.util.LinkedList;
import java.util.List;

public class Lexer {
	public String input = null;
	public int currentPosition = 0;

	public Lexer(String inp) {
		input = inp;
	}

	void skipWhitespace() {
		while (currentPosition < input.length()
				&& Character.isWhitespace(input.charAt(currentPosition))) {
			currentPosition++;
		}
	}

	private boolean match(char c) {
		return currentPosition < input.length()
				&& input.charAt(currentPosition) == c;
	}

	public Token nextToken() {
		skipWhitespace();
		if (currentPosition >= input.length()) {
			return new Token(TokenType.TOK_EOF);
		}

		// From here on, we have at least one character in the input

		// Positive Integers
		if (Character.isDigit(input.charAt(currentPosition))) {
			return lexInteger(false);
		}

		
		// Mathematical operations (+,-,*,/,%) + negative integer
		if (match('+')) {
			currentPosition++;
			return new Token(TokenType.TOK_PLUS);
		}

		if (match('-')) {
			currentPosition++;
			// Negative integer
			if (input.length() < currentPosition && Character.isDigit(input.charAt(currentPosition))) {
				currentPosition++;
				return lexInteger(true);
			}
			if (match('>')) {
				currentPosition++;
				return new Token(TokenType.TOK_MAPSTO);
			}
			return new Token(TokenType.TOK_MINUS);
		}

		if (match('*')) {
			currentPosition++;
			return new Token(TokenType.TOK_MULT);
		}

		if (match('/')) {
			currentPosition++;
			if (input.length() > currentPosition) {
				if (input.charAt(currentPosition) == '/') {
					currentPosition++;
					while(input.charAt(currentPosition) != '\n') {
						currentPosition++;
					}
					currentPosition++;
					return nextToken();
				} else if (input.charAt(currentPosition) == '*') {
					currentPosition++;
					while(!(input.substring(currentPosition, currentPosition+2).equals("*/"))) {
						currentPosition++;
					}
					currentPosition +=2;
					return nextToken();
				}
			}
			return new Token(TokenType.TOK_DIV);
		}

		if (match('%')) {
			currentPosition++;
			return new Token(TokenType.TOK_MOD);
		}

		
		// Boolean operators + assignment
		// Assignment
		if (match('=')) {
			currentPosition++;
			// Boolean operator equals
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_EQUALS);
			}
			return new Token(TokenType.TOK_ASS);
		}

		if (match('<')) {
			currentPosition++;
			// Lesser or equal
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_LEQ);
			}
			// Stricly lesser
			return new Token(TokenType.TOK_LESS);
		}

		if (match('>')) {
			currentPosition++;
			// Greater or equal
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_GEQ);
			}
			// Strictly greater
			return new Token(TokenType.TOK_GREAT);
		}

		if (match('!')) {
			currentPosition++;
			// 'Not equal to' operator
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_NEQ);
			}
			// Negation
			return new Token(TokenType.TOK_NEG);
		}

		// Boolean AND
		if (match('&')) {
			currentPosition++;
			if (match('&')) {
				currentPosition++;
				return new Token(TokenType.TOK_AND);
			}
			return new Token(TokenType.TOK_BEGIN_CONCAT);
		}

		// Boolean OR
		if (match('|')) {
			currentPosition++;
			if (match('|')) {
				currentPosition++;
				return new Token(TokenType.TOK_OR);
			}
			return new TokenError("Unknown character in input: '"
					+ input.charAt(currentPosition) + "'");
		}

		
		// All types of brackets
		if (match('(')) {
			currentPosition++;
			return new Token(TokenType.TOK_BRACK_OPEN);
		}

		if (match(')')) {
			currentPosition++;
			return new Token(TokenType.TOK_BRACK_CLOSE);
		}

		if (match('[')) {
			currentPosition++;
			return new Token(TokenType.TOK_LIST_OPEN);
		}

		if (match(']')) {
			currentPosition++;
			return new Token(TokenType.TOK_LIST_CLOSE);
		}

		if (match('{')) {
			currentPosition++;
			return new Token(TokenType.TOK_BLOCK_OPEN);
		}

		if (match('}')) {
			currentPosition++;
			return new Token(TokenType.TOK_BLOCK_CLOSE);
		}

		
		// Misc.
		if (match(':')) {
			currentPosition++;
			// :: is used for denoting function types
			if (match(':')) {
				currentPosition++;
				return new Token(TokenType.TOK_FUNTYPE);
			}
			// : is the concatenation operator
			return new Token(TokenType.TOK_CONCAT);
		}

		if (match('.')) {
			currentPosition++;
			return new Token(TokenType.TOK_DOT);
		}

		if (match(',')) {
			currentPosition++;
			return new Token(TokenType.TOK_COMMA);
		}
		// Chars
		if (match('\'')) {
			currentPosition++;
			if (Character.isAlphabetic(input.charAt(currentPosition))) {
				char value = input.charAt(currentPosition);
				currentPosition++;
				if (match('\'')) {
					currentPosition++;
					return new TokenChar(value);
				}
			}
			return new TokenError("Unknown character in input: '"
					+ input.charAt(currentPosition) + "'");
		}

		// End of statement
		if (match(';')) {
			currentPosition++;
			return new Token(TokenType.TOK_EOS);
		}

		// Identifier
		if (Character.isAlphabetic(input.charAt(currentPosition))) {
			return lexIdentifier();
		}

		return new TokenError("Unknown character in input: '"
				+ input.charAt(currentPosition) + "'");
	}

	/**
	 * Returns a token with the value of the current string of digits
	 * @param negative If true, the token will have the negative value, otherwise it will be positive
	 * @return The TokenInteger with the correct value
	 */
	Token lexInteger(boolean negative) {
		int currentValue = 0;
		while (currentPosition < input.length()
				&& Character.isDigit(input.charAt(currentPosition))) {
			currentValue *= 10;
			currentValue += Character.getNumericValue(input
					.charAt(currentPosition));
			currentPosition++;
		}
		if (negative)
			return new TokenInteger(-currentValue);
		else
			return new TokenInteger(currentValue);
	}

	Token lexIdentifier() {
		StringBuilder resultBuilder = new StringBuilder();
		while (currentPosition < input.length()
				&& (Character.isAlphabetic(input.charAt(currentPosition)) || Character
						.isDigit(input.charAt(currentPosition)))) {
			resultBuilder.append(input.charAt(currentPosition));
			currentPosition++;
		}

		String result = resultBuilder.toString();

		// Reserved keywords
		if (result.equals("new")) {
			return new Token(TokenType.TOK_KW_NEW);
		}
		if (result.equals("print")) {
			return new Token(TokenType.TOK_KW_PRINT);
		}
		if (result.equals("null")) {
			return TokenNull.instanceOf;
		}
		if (result.equals("var")) {
			return new Token(TokenType.TOK_KW_VAR);
		}
		
		if (result.equals("Void")) {
			return new Token(TokenType.TOK_KW_VOID);
		}
		
		if (result.equals("if")) {
			return new Token(TokenType.TOK_KW_IF);
		}
		
		if (result.equals("else")) {
			return new Token(TokenType.TOK_KW_ELSE);
		}
		
		if (result.equals("while"))
			return new Token(TokenType.TOK_KW_WHILE);
		
		if (result.equals("return")) {
			return new Token(TokenType.TOK_KW_RETURN);
		}
		
		
		// List/tuple basic functions
		
		if (result.equals("hd")) {
			return new Token(TokenType.TOK_LISTFUNC_HEAD);
		}
		
		if (result.equals("tl")) {
			return new Token(TokenType.TOK_LISTFUNC_TAIL);
		}
		
		if (result.equals("fst")) {
			return new TokenTupleFunction(TupleFunction.TUPLEFUNC_FIRST);
		}
		
		if (result.equals("snd")) {
			return new TokenTupleFunction(TupleFunction.TUPLEFUNC_SECOND);
		}
		
		
		// Basic types
		
		if (result.equals("Int")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_INT);
		}
		
		if (result.equals("Bool")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_BOOL);
		}
		
		if (result.equals("Char")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_CHAR);
		}

		
		// Boolean values
		
		if (result.equals("True")) {
			return new TokenBool(true);
		}

		if (result.equals("False")) {
			return new TokenBool(false);
		}

		
		// Identifier is not a keyword, so we treat it as identifier
		return new TokenIdentifier(result);
	}

	public List<Token> allNextTokens() {
		List<Token> out = new LinkedList<>();
		Token token;
		while(!(token = nextToken()).getTokenType().equals(TokenType.TOK_EOF)) {
			out.add(token);
		}
		out.add(new Token(TokenType.TOK_EOF));
		return out;
	}
}
