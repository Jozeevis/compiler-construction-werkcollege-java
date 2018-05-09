package lexer;

public enum TokenType {
	TOK_EOF, TOK_ERR, TOK_INT, TOK_PLUS, TOK_MINUS, TOK_MULT, TOK_PLUS_EQUALS, TOK_IDENTIFIER, TOK_KW_IF, TOK_BOOL, TOK_KW_VAR
		, TOK_KW_VOID, TOK_KW_ELSE, TOK_KW_RETURN,TOK_EOS, TOK_MAPSTO, TOK_DOT, TOK_BLOCK_CLOSE, TOK_BLOCK_OPEN, TOK_LIST_CLOSE
		, TOK_LIST_OPEN, TOK_BRACK_CLOSE, TOK_BRACK_OPEN, TOK_COMMA, TOK_CONCAT, TOK_FUNTYPE, TOK_OR, TOK_AND, TOK_DIV, TOK_MOD
		, TOK_EQUALS, TOK_ASS, TOK_LEQ, TOK_LESS, TOK_GEQ, TOK_GREAT, TOK_NEQ, TOK_NEG, TOK_PRIM_TYPE, TOK_PRIM_FUNC_LIST
		, TOK_PRIM_FUNC_TUPLE, TOK_CHAR, TOK_KW_WHILE, TOK_EXP, TOK_NIL;
}