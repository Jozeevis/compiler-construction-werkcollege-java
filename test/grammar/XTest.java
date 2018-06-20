/**
 * 
 */
package grammar;

import org.junit.Test;

import lexer.Lexer;
import parser.Parser;
import processing.TreeProcessing;
import tree.CodeGenerator;
import tree.SyntaxTree;

/**
 * @author Flip van Spaendonck
 *
 */
public class XTest {

	
	@Test
	public void testX() {
		String code = "	main () ::->void { \r\n" + 
				"	[Int] list = & 1 : 2 : [];"
				+ " list.tl = & 3 : [];"
				+ " return;\r\n"+ 
				"	}" ;
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
