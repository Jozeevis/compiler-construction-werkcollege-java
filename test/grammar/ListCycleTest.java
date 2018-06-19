package grammar;

import org.junit.Test;

import lexer.Lexer;
import parser.Parser;
import processing.TreeProcessing;
import tree.CodeGenerator;
import tree.SyntaxTree;

public class ListCycleTest {
	
	@Test
	public void listCycleTest() {
		String code = "printNumElements(list, n) :: [Int] Int -> void\r\n" + 
				"{\r\n" + 
				"    while( !isEmpty list && n > 0 )\r\n" + 
				"    {\r\n" + 
				"        print list.hd;\r\n" + 
				"        list = list.tl;\r\n" + 
				"        n = n - 1;\r\n" + 
				"    }\r\n" + 
				"}" +
				"main () :: -> void {\r\n" +
			        "[Int] a = & 2 : 3 : 4 : [];\r\n" +
					"a.tl.tl = a;\r\n" +
			        "printNumElements(a, 10);\r\n" +
		            "return;\r\n" +
		        "}\r\n" ;
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
		try {
			SyntaxTree t = TreeProcessing.processIntoAST(p.tree.root);
			System.out.println("=====");
			System.out.println(t);
			System.out.println("=====");
			
			System.out.println(TreeProcessing.checkWellTyped(t));
			System.out.println(CodeGenerator.generateCode(t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
