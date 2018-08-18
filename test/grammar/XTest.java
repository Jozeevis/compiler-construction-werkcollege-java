/**
 * 
 */
package grammar;

import java.util.List;

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
		//String code = " Dog { [Int] securityCode = &1:[]; Dog() {print securityCode.hd;}} main () ::->void {Dog dog = new Dog(); print dog.securityCode.hd;}" ;
		String code = "main()::->void\r\n" + 
				"{\r\n" + 
				"    [Int] a = & 7:[];\r\n" + 
				"    [Int] b = & 10:a;\r\n" + 
				"\r\n" + 
				"    // 7\r\n" + 
				"    print(a.hd);\r\n" + 
				"    // 10\r\n" + 
				"    print(b.hd);\r\n" + 
				"    // 7\r\n" + 
				"    print(b.tl.hd);\r\n" + 
				"\r\n" + 
				"    a.hd = 8;\r\n" + 
				"    b.hd = 11;\r\n" + 
				"\r\n" + 
				"    // 8\r\n" + 
				"    print(a.hd);\r\n" + 
				"    // 11\r\n" + 
				"    print(b.hd);\r\n" + 
				"    // 8\r\n" + 
				"    print(b.tl.hd);\r\n" + 
				"\r\n" + 
				"return;\r\n" + 
				"}";
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
		System.out.println("code parsed");
		try {
			
			SyntaxTree t = TreeProcessing.processIntoAST(p.tree.root);
			System.out.println("=====");
			System.out.println(t);
			System.out.println("=====");
			
			System.out.println(TreeProcessing.checkWellTyped(t));
			System.out.println("=====");
			List<String> ssm =  CodeGenerator.generateCode(t);
			for(String ssmLine : ssm) {
				System.out.print(ssmLine);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
