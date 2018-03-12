package parser;

import java.util.LinkedList;
import java.util.List;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;

public class Parser {
	private Lexer lexer;
	private Token currentToken;
	private List<Token> tokenList;

	// =========================================================
	// Constructors
	// =========================================================

	public Parser(Lexer l) {
		lexer = l;
		tokenList = new LinkedList<>();
		while(true) {
			Token token = next();
			if (token.getTokenType() == TokenType.TOK_EOF)
				break;
			else
				tokenList.add(token);
		}
		
	}

	public Parser(String input) {
		this(new Lexer(input));
	}

	// =========================================================
	// Helper functions
	// =========================================================

	// Helper function to fetch the next token. Convention: A parser calls next
	// whenever it consumes a token.
	private Token next() {
		Token oldToken = currentToken;
		currentToken = lexer.nextToken();
		return oldToken;
	}

	// Helper function to check the type of the current token
	private boolean match(TokenType tok) {
		return currentToken.getTokenType() == tok;
	}

	// Helper function to generate error messages
	private Error error(String name) {
		return new Error(name + ": unexpected token "
				+ currentToken.getTokenType().toString());
	}

	// ==========================================================
	// The actual parser
	// ==========================================================

	public AstExpr pExpr() {
		AstExpr lhs = pTerm();
		return pExpr_(lhs);
	}

	public AstExpr pExpr_(AstExpr lhs) {
		// Plus and minus have the same precedence, therefore they are in the
		// same rule
		if (match(TokenType.TOK_PLUS)) {
			next();
			AstExpr rhs = pTerm();
			return pExpr_(new AstExprBinOp(lhs, TokenType.TOK_PLUS, rhs));
		}

		if (match(TokenType.TOK_MINUS)) {
			next();
			AstExpr rhs = pTerm();
			return pExpr_(new AstExprBinOp(lhs, TokenType.TOK_MINUS, rhs));
		}

		return lhs;
	}

	public AstExpr pTerm() {
		AstExpr lhs = pFactor();
		return pTerm_(lhs);
	}

	private AstExpr pTerm_(AstExpr lhs) {
		if (match(TokenType.TOK_MULT)) {
			next();
			AstExpr rhs = pFactor();
			return pTerm_(new AstExprBinOp(lhs, TokenType.TOK_MULT, rhs));
		}
		return lhs;
	}

	public AstExpr pFactor() {
		if (match(TokenType.TOK_INT)) {
			return new AstExprInteger(next());
		}
		if( match(TokenType.TOK_BOOL)) {
			return new AstExprBool(next());
		}

		throw error("pFactor");
	}

}
