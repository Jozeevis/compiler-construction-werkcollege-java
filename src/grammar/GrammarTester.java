package grammar;

import lexer.Lexer;
import parser.Parser;

public class GrammarTester {

	public static void main(String[] args) {
		String code = "main :: -> Void {print 'h';}";
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
	}

}
