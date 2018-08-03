package lexer;

import java.util.LinkedList;
import java.util.List;

public class Lexer {
	public static final int STARTING_LINE_NUMBER = 1;
	public String input = null;
	public int currentPosition = 0;
	public int lineNumber = STARTING_LINE_NUMBER;
	
	public Lexer(String inp) {
		input = inp;
	}

	void skipWhitespace() {
		while (currentPosition < input.length()) {
			if (input.charAt(currentPosition) == '\n')
				lineNumber ++;
			if (Character.isWhitespace(input.charAt(currentPosition))) {
				currentPosition++;
			} else
				return;
		}
	}

	private boolean match(char c) {
		return currentPosition < input.length()
				&& input.charAt(currentPosition) == c;
	}

	public Token nextToken() throws LexingException {
		skipWhitespace();
		if (currentPosition >= input.length()) {
			return new Token(TokenType.TOK_EOF, lineNumber);
		}

		// From here on, we have at least one character in the input

		// Positive Integers
		if (Character.isDigit(input.charAt(currentPosition))) {
			return lexInteger(false);
		}

		
		// Mathematical operations (+,-,*,/,%) + negative integer
		if (match('+')) {
			currentPosition++;
			return new Token(TokenType.TOK_PLUS, lineNumber);
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
				return new Token(TokenType.TOK_MAPSTO, lineNumber);
			}
			return new Token(TokenType.TOK_MINUS, lineNumber);
		}

		if (match('*')) {
			currentPosition++;
			return new Token(TokenType.TOK_MULT, lineNumber);
		}

		if (match('/')) {
			currentPosition++;
			if (input.length() > currentPosition) {
				if (input.charAt(currentPosition) == '/') {
					currentPosition++;
					while(input.length() > currentPosition && input.charAt(currentPosition) != '\n') {
						currentPosition++;
					}
					lineNumber++;
					currentPosition++;
					return nextToken();
				} else if (input.charAt(currentPosition) == '*') {
					currentPosition++;
					while(!(input.substring(currentPosition, currentPosition+2).equals("*/"))) {
						if (input.charAt(currentPosition) == '\n')
							lineNumber++;
						currentPosition++;
						if (currentPosition >= input.length()) {
							throw new LexingException("Multiline comment (/*) found at line "+lineNumber+"but not ended (*/).");
						}
					}
					currentPosition +=2;
					return nextToken();
				}
			}
			return new Token(TokenType.TOK_DIV, lineNumber);
		}

		if (match('%')) {
			currentPosition++;
			return new Token(TokenType.TOK_MOD, lineNumber);
		}

		
		// Boolean operators + assignment
		// Assignment
		if (match('=')) {
			currentPosition++;
			// Boolean operator equals
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_EQUALS, lineNumber);
			}
			return new Token(TokenType.TOK_ASS, lineNumber);
		}

		if (match('<')) {
			currentPosition++;
			// Lesser or equal
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_LEQ, lineNumber);
			}
			// Stricly lesser
			return new Token(TokenType.TOK_LESS, lineNumber);
		}

		if (match('>')) {
			currentPosition++;
			// Greater or equal
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_GEQ, lineNumber);
			}
			// Strictly greater
			return new Token(TokenType.TOK_GREAT, lineNumber);
		}

		if (match('!')) {
			currentPosition++;
			// 'Not equal to' operator
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_NEQ, lineNumber);
			}
			// Negation
			return new Token(TokenType.TOK_NEG, lineNumber);
		}

		// Boolean AND
		if (match('&')) {
			currentPosition++;
			if (match('&')) {
				currentPosition++;
				return new Token(TokenType.TOK_AND, lineNumber);
			}
			return new Token(TokenType.TOK_BEGIN_CONCAT, lineNumber);
		}

		// Boolean OR
		if (match('|')) {
			currentPosition++;
			if (match('|')) {
				currentPosition++;
				return new Token(TokenType.TOK_OR, lineNumber);
			}
			throw new LexingException("\"|\" encountered at line "+lineNumber+" but followed by unknown character: "+input.charAt(currentPosition));
		}

		
		// All types of brackets
		if (match('(')) {
			currentPosition++;
			return new Token(TokenType.TOK_BRACK_OPEN, lineNumber);
		}

		if (match(')')) {
			currentPosition++;
			return new Token(TokenType.TOK_BRACK_CLOSE, lineNumber);
		}

		if (match('[')) {
			currentPosition++;
			return new Token(TokenType.TOK_LIST_OPEN, lineNumber);
		}

		if (match(']')) {
			currentPosition++;
			return new Token(TokenType.TOK_LIST_CLOSE, lineNumber);
		}

		if (match('{')) {
			currentPosition++;
			return new Token(TokenType.TOK_BLOCK_OPEN, lineNumber);
		}

		if (match('}')) {
			currentPosition++;
			return new Token(TokenType.TOK_BLOCK_CLOSE, lineNumber);
		}

		
		// Misc.
		if (match(':')) {
			currentPosition++;
			// :: is used for denoting function types
			if (match(':')) {
				currentPosition++;
				return new Token(TokenType.TOK_FUNTYPE, lineNumber);
			}
			// : is the concatenation operator
			return new Token(TokenType.TOK_CONCAT, lineNumber);
		}

		if (match('.')) {
			currentPosition++;
			return new Token(TokenType.TOK_DOT, lineNumber);
		}

		if (match(',')) {
			currentPosition++;
			return new Token(TokenType.TOK_COMMA, lineNumber);
		}
		// Chars
		if (match('\'')) {
			currentPosition++;
			if (input.charAt(currentPosition) >= 32) {
				char value = input.charAt(currentPosition);
				currentPosition++;
				if (match('\'')) {
					currentPosition++;
					return new TokenChar(value, lineNumber);
				}
			}
			
			
		}

		// End of statement
		if (match(';')) {
			currentPosition++;
			return new Token(TokenType.TOK_EOS, lineNumber);
		}

		// Identifier
		if (Character.isAlphabetic(input.charAt(currentPosition))) {
			return lexIdentifier();
		}

		throw new LexingException("\" encountered at line "+lineNumber+" but followed by unknown character: "+input.charAt(currentPosition));
		
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
			return new TokenInteger(-currentValue, lineNumber);
		else
			return new TokenInteger(currentValue, lineNumber);
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
		if(result.equals("this")) {
			return new Token(TokenType.TOK_KW_THIS, lineNumber);
		}
		if (result.equals("new")) {
			return new Token(TokenType.TOK_KW_NEW, lineNumber);
		}
		if (result.equals("print")) {
			return new Token(TokenType.TOK_KW_PRINT, lineNumber);
		}
		if (result.equals("isEmpty")) {
			return new Token(TokenType.TOK_KW_ISEMPTY, lineNumber);
		}
		if (result.equals("null")) {
			return new Token(TokenType.TOK_KW_NULL, lineNumber);
		}
		if (result.equals("var")) {
			return new Token(TokenType.TOK_KW_VAR, lineNumber);
		}
		
		if (result.equals("void")) {
			return new Token(TokenType.TOK_KW_VOID, lineNumber);
		}
		
		if (result.equals("if")) {
			return new Token(TokenType.TOK_KW_IF, lineNumber);
		}
		
		if (result.equals("else")) {
			return new Token(TokenType.TOK_KW_ELSE, lineNumber);
		}
		
		if (result.equals("while"))
			return new Token(TokenType.TOK_KW_WHILE, lineNumber);
		
		if (result.equals("return")) {
			return new Token(TokenType.TOK_KW_RETURN, lineNumber);
		}
		
		
		// List/tuple basic functions
		
		if (result.equals("hd")) {
			return new Token(TokenType.TOK_LISTFUNC_HEAD, lineNumber);
		}
		
		if (result.equals("tl")) {
			return new Token(TokenType.TOK_LISTFUNC_TAIL, lineNumber);
		}
		
		if (result.equals("fst")) {
			return new TokenTupleFunction(TupleFunction.TUPLEFUNC_FIRST, lineNumber);
		}
		
		if (result.equals("snd")) {
			return new TokenTupleFunction(TupleFunction.TUPLEFUNC_SECOND, lineNumber);
		}
		
		
		// Basic types
		
		if (result.equals("Int")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_INT, lineNumber);
		}
		
		if (result.equals("Bool")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_BOOL, lineNumber);
		}
		
		if (result.equals("Char")) {
			return new TokenPrimitiveType(PrimitiveType.PRIMTYPE_CHAR, lineNumber);
		}

		
		// Boolean values
		
		if (result.equals("True")) {
			return new TokenBool(true, lineNumber);
		}

		if (result.equals("False")) {
			return new TokenBool(false, lineNumber);
		}

		
		// Identifier is not a keyword, so we treat it as identifier
		return new TokenIdentifier(result, lineNumber);
	}

	public List<Token> allNextTokens() throws LexingException {
		List<Token> out = new LinkedList<>();
		Token token;
		while(!(token = nextToken()).getTokenType().equals(TokenType.TOK_EOF)) {
			out.add(token);
		}
		out.add(new Token(TokenType.TOK_EOF, lineNumber));
		return out;
	}
}
