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

	/*@Test
	public void testEXPParsing() {
		//EXP.INSTANCE.printTree();
		String code = "'h'";
		Lexer l = new Lexer(code);
		List<Token> tokens = l.allNextTokens();
		List<TokenTrace> tt = Parser.explorino(EXP.INSTANCE, tokens);
		SyntaxTree t = Parser.convertZambinos(tt);
		
		System.out.println(((ExpressionWithAST)((SyntaxExpressionKnot)t.root).expression).id);
	}*/
	
	@Test
	public void testHelloWorld() {
		String code = "main() :: -> Void {print 'h';print 'e';}";
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
		
		/*
		 * Dear Lars,
		 * the code seems to get stuck while parsing, after the expressions have been packed.
		 * It seems to be happening at the construction of SPL.
		 */
	}
}
