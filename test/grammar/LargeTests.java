/**
 * 
 */
package grammar;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import parser.Parser;
import parser.Parser.TokenTrace;
import processing.TreeProcessing;
import tree.CodeGenerator;
import tree.SyntaxExpressionKnot;
import tree.SyntaxTree;

/**
 * @author Flip van Spaendonck
 *
 */
public class LargeTests {
	
	@Test
	public void testHelloWorld() {
		String code = "printPentuple( muple ) :: (Char, Char, Char, Char, Char) -> Char {Int i = 4; print muple.[0]; print muple.[1]; print muple.[2]; print muple.[3]; print muple.[4]; return '!';}"
				+ "main() :: -> void {(Char, Char, Char, Char, Char) hello = ('h', 'e', 'l', 'l', 'o'); print printPentuple( hello);}";
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
		System.out.println(p.tree.root);
		try {
			
			SyntaxTree t = TreeProcessing.processIntoAST(p.tree.root);
			System.out.println(TreeProcessing.checkWellTyped(t));
			System.out.println(CodeGenerator.generateCode(t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
