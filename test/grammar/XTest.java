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
		String code = "main () ::->void {[Int] a = &3:[]; print 't'; a.tl = &9:6:4:20:[]; print a.tl.tl.tl.tl.hd;}";
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
