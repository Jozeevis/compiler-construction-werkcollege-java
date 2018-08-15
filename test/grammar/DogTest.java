package grammar;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import lexer.TokenType;
import parser.Parser;
import parser.ParsingException;
import processing.TreeProcessing;
import tree.CodeGenerator;
import tree.SyntaxTree;
import tree.ast.expressions.IllegalThisException;

public class DogTest {

	/*@Test
	public void dogSpression() {
		List<Token> expression = new LinkedList<>();
		expression.add(new Token(TokenType.TOK_KW_NEW));
		expression.add(new TokenIdentifier("Dog"));
		expression.add(new Token(TokenType.TOK_BRACK_OPEN));
		expression.add(new Token(TokenType.TOK_BRACK_CLOSE));
		try {
			TokenExpression tok = new TokenExpression(expression);
			System.out.println(tok.expression);
		} catch (IllegalThisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void smollDogTest() {
		String code = " main () ::->void { \r\n" + 
			"	Dog dog = new Dog();" +
			" return;\r\n"+ 
			"	}";
		System.out.println(code);
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
		System.out.println(p.tree);
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
	}*/
	
	@Test
	public void dogTest() {
		String code = "	main () ::->void { Dog dog = new Dog(); }" ;
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
