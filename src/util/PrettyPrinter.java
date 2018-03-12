package util;

import lexer.TokenType;
import parser.AstExprBool;
import parser.AstExprBinOp;
import parser.AstExprInteger;

public class PrettyPrinter implements Visitor {
	StringBuilder result;

	public String getResultString() {
		return result.toString();
	}

	public PrettyPrinter() {
		result = new StringBuilder();
	}

	private void printToken(Token t) {
		switch (t.getTokenType()) {

		// Mathematical operators
		case TOK_PLUS:
			result.append(" + ");
		break;
		case TOK_MINUS:
			result.append(" - ");
		break;
		case TOK_MULT:
			result.append(" * ");
		break;
		case TOK_DIV:
			result.append(" / ");
		break;
		case TOK_MOD:
			result.append(" % ");
		break;

		// Boolean operators
		case TOK_NEG:
			result.append("! ");
		break;
		case TOK_OR:
			result.append(" || ");
		break;
		case TOK_AND:
			result.append(" && ");
		break;

		// Comparison operators
		case TOK_EQUALS:
			result.append(" == ");
		break;
		case TOK_NEQ:
			result.append(" != ");
		break;
		case TOK_LEQ:
			result.append(" <= ");
		break;
		case TOK_LESS:
			result.append(" < ");
		break;
		case TOK_GEQ:
			result.append(" >= ");
		break;
		case TOK_GREAT:
			result.append(" > ");
		break;

		// Concatenation operator
		case TOK_CONCAT:
			result.append(" : ");
		break;

		// Keywords
		case TOK_KW_IF:
			result.append("if ");
		break;
		case TOK_KW_ELSE:
			result.append(" else ");
		break;
		case TOK_KW_WHILE:
			result.append("while ");
		break;
		case TOK_KW_VAR:
			result.append("var ");
		break;
		case TOK_KW_VOID:
			result.append("Void");
		break;
		case TOK_KW_RETURN:
			result.append("return ");
		break;

		// All bracket types
		case TOK_BLOCK_OPEN:
			result.append("{ ");
		break;
		case TOK_BLOCK_CLOSE:
			result.append(" }");
		break;
		case TOK_LIST_OPEN:
			result.append("[ ");
		break;
		case TOK_LIST_CLOSE:
			result.append(" ]");
		break;
		case TOK_BRACK_OPEN:
			result.append("( ");
		break;
		case TOK_BRACK_CLOSE:
			result.append(" )");
		break;

		// Misc.
		case TOK_EOS:
			result.append(";\n");
		break;
		case TOK_ASS:
			result.append(" = ");
		break;
		case TOK_MAPSTO:
			result.append(" -> ");
		break;
		case TOK_FUNTYOE:
			result.append(" :: ");
		break;
		case TOK_DOT:
			result.append(".");
		break;
		case TOK_COMMA:
			result.append(", ");
		break;

		// Primitive types instances
		case TOK_BOOL:
			result.append(t.getValue() ? "true" : "false");
		break;
		case TOK_INT:
			result.append(t.getValue());
		break;
		case TOK_CHAR:
			result.append("'" + t.getValue() + "'");
		break;

		// Primitive type token
		case TOK_PRIM_TYPE:
			String output = '';
			switch(t.getType()): {
				case PRIMTYPE_BOOL: output = "Bool "; break;
				case PRIMTYPE_CHAR: output = "Char "; break;
				case PRIMTYPE_INT: output = "Int "; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
				}
			}
			result.append(output);
		break;

		// Primitive list functions
		case TOK_PRIM_FUNC_LIST:
			String output = '';
			switch(t.getType()): {
				case LISTFUNC_HEAD: output = "hd"; break;
				case LISTFUNC_TAIL: output = "tl"; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
				}
			}
			result.append(output);
		break;

		// Primitive tuple functions
		case TOK_PRIM_FUNC_TUPLE:
			String output = '';
			switch(t.getType()): {
				case TUPLEFUNC_FIRST: output = "fst"; break;
				case TUPLEFUNC_SECOND: output = "snd"; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
				}
			}
			result.append(output);
		break;

		// Identifier
		case TOK_IDENTIFIER:
			result.append(t.getValue());
		break;

		default:
			throw new Error("PrettyPrinter: cannot print token " + t);
		}
	}

	@Override
	public void visit(AstExprInteger i) {
		result.append(i.getValue());
	}

	@Override
	public void visit(AstExprBinOp e) {
		e.getLeft().accept(this);
		printToken(e.getOperator());
		e.getRight().accept(this);
	}

	@Override
	public void visit(AstExprBool astBool) {
		// TODO Auto-generated method stub		
	}
}
