package util;

import java.util.List;
import java.util.ListIterator;
import lexer.Token;

import lexer.TokenBool;
import lexer.TokenChar;
import lexer.TokenInteger;

import lexer.TokenPrimitiveType;
import lexer.TokenListFunction;
import lexer.TokenTupleFunction;
import lexer.TokenIdentifier;

public class PrettyPrinter {
	StringBuilder result;

	public String getResultString(List<Token> input) {
		ListIterator<Token> li = input.listIterator();
		while(li.hasNext())
			printToken(li.next());
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
		case TOK_FUNTYPE:
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
			result.append(((TokenBool)t).getValue() ? "true" : "false");
		break;
		case TOK_INT:
			result.append(((TokenInteger)t).getValue());
		break;
		case TOK_CHAR:
			result.append("'" + ((TokenChar)t).getValue() + "'");
		break;

		// Primitive type token
		case TOK_PRIM_TYPE:
			String output = "";
			switch(((TokenPrimitiveType)t).getType()) {
				case PRIMTYPE_BOOL: output = "Bool "; break;
				case PRIMTYPE_CHAR: output = "Char "; break;
				case PRIMTYPE_INT: output = "Int "; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
			}
			result.append(output);
		break;

		// Primitive list functions
		case TOK_PRIM_FUNC_LIST:
			output = "";
			switch(((TokenListFunction)t).getType()) {
				case LISTFUNC_HEAD: output = "hd"; break;
				case LISTFUNC_TAIL: output = "tl"; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
			}
			result.append(output);
		break;

		// Primitive tuple functions
		case TOK_PRIM_FUNC_TUPLE:
			output = "";
			switch(((TokenTupleFunction)t).getType()) {
				case TUPLEFUNC_FIRST: output = "fst"; break;
				case TUPLEFUNC_SECOND: output = "snd"; break;
				default:
					throw new Error("PrettyPrinter: cannot print token " + t);
			}
			result.append(output);
		break;

		// Identifier
		case TOK_IDENTIFIER:
			result.append(((TokenIdentifier)t).getValue());
		break;

		default:
			throw new Error("PrettyPrinter: cannot print token " + t);
		}
	}


}
