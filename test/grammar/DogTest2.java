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
public class DogTest2 {

	@Test
	public void dogTest2() {
		String code = "Dog {\r\n" + 
				"	Int nrOfBarksLeft = 5;\r\n" + 
				"	Dog() {bark0(2);}\r\n" + 
				"	bark0(nr) :: Int -> void {\r\n" + 
				"	    print 'b';\r\n" + 
				"		if (nr > 0) {bark1(nr-1);}\r\n" + 
				"	}\r\n" + 
				"	bark1(nr) :: Int -> void {\r\n" + 
				"		print 'b';\r\n" + 
				"		bark0(nr-1);\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	barkRecursive() :: -> void {\r\n" + 
				"		print 'b';\r\n" + 
				"		if (nrOfBarksLeft > 0) {\r\n" + 
				"			nrOfBarksLeft = nrOfBarksLeft - 1;\r\n" + 
				"			barkRecursive();\r\n" + 
				"		}\r\n" + 
				"	}\r\n }" +
				"  \r\n" + 
				"	main () ::->void { \r\n" + 
				"	Dog dog = new Dog(); return;\r\n"+ 
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
