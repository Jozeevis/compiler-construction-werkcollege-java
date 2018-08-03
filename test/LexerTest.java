import static org.junit.Assert.*;
import lexer.Lexer;
import lexer.LexingException;
import lexer.Token;
import lexer.TokenBool;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import lexer.TokenType;

import org.junit.Test;

public class LexerTest {

	@Test
	public void testEmptyString() throws LexingException {
		Lexer l = new Lexer("");
		assertEquals(TokenType.TOK_EOF, l.nextToken().getTokenType());
	}

	@Test
	public void testSingleDigitInteger() throws LexingException {
		Lexer l = new Lexer("5");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(5, ((TokenInteger) t).getValue());
	}

	@Test
	public void testMultiDigitInteger() throws LexingException {
		Lexer l = new Lexer("4545372");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(4545372, ((TokenInteger) t).getValue());
	}

	@Test
	public void testSinglePlus() throws LexingException {
		Lexer l = new Lexer("+");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_PLUS, t.getTokenType());
	}

	@Test
	public void testSingleMinus() throws LexingException {
		Lexer l = new Lexer("-");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MINUS, t.getTokenType());
	}
	
	@Test
	public void testMapsTo() throws LexingException {
		Lexer l = new Lexer("->");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MAPSTO, t.getTokenType());
	}

	@Test
	public void testSingleMult() throws LexingException {
		Lexer l = new Lexer("*");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MULT, t.getTokenType());
	}

	@Test
	public void testMultipleIntegers() throws LexingException {
		Lexer l = new Lexer("12 34");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_INT, t2.getTokenType());
		assertEquals(12, ((TokenInteger) t1).getValue());
		assertEquals(34, ((TokenInteger) t2).getValue());
	}

	@Test
	public void testSimpleExpression() throws LexingException {
		Lexer l = new Lexer("1 + 12 + 300+4+");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		Token t3 = l.nextToken();
		Token t4 = l.nextToken();
		Token t5 = l.nextToken();
		Token t6 = l.nextToken();
		Token t7 = l.nextToken();
		Token t8 = l.nextToken();
		Token t9 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t2.getTokenType());
		assertEquals(TokenType.TOK_INT, t3.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t4.getTokenType());
		assertEquals(TokenType.TOK_INT, t5.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t6.getTokenType());
		assertEquals(TokenType.TOK_INT, t7.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t8.getTokenType());
		assertEquals(TokenType.TOK_EOF, t9.getTokenType());
	}

	@Test
	public void testMixedPlussesAndMinusesExpression() throws LexingException {
		Lexer l = new Lexer("1 - 12 + 300-4-");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		Token t3 = l.nextToken();
		Token t4 = l.nextToken();
		Token t5 = l.nextToken();
		Token t6 = l.nextToken();
		Token t7 = l.nextToken();
		Token t8 = l.nextToken();
		Token t9 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t2.getTokenType());
		assertEquals(TokenType.TOK_INT, t3.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t4.getTokenType());
		assertEquals(TokenType.TOK_INT, t5.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t6.getTokenType());
		assertEquals(TokenType.TOK_INT, t7.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t8.getTokenType());
		assertEquals(TokenType.TOK_EOF, t9.getTokenType());
	}

	@Test
	public void testIdentifier() throws LexingException {
		Lexer l = new Lexer("missPiggy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("missPiggy", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testIdentifierWithDigits() throws LexingException {
		Lexer l = new Lexer("mi55Piggy23");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("mi55Piggy23", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testKeywordIf() throws LexingException {
		Lexer l = new Lexer("if");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_KW_IF, t.getTokenType());
	}

	@Test
	public void testKeywordPrefix() throws LexingException {
		Lexer l = new Lexer("iffy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
	}

	@Test
	public void testBooleanConstantTrue() throws LexingException {
		Lexer l = new Lexer("True");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_BOOL, t.getTokenType());
		assertEquals(true, ((TokenBool)t).getValue());
	}
	
	@Test
	public void testSingleLineComment() throws LexingException {
		Lexer l = new Lexer("//adasdasdasdasdasd \n 1");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
	}
	
	@Test
	public void testMultyLineComment() throws LexingException {
		Lexer l = new Lexer("/*adasdasdas \n \n \ndasdasd \n */1");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
	}
	
	@Test
	public void testPrintKW() throws LexingException {
		Lexer l = new Lexer("print 3+3+4");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_KW_PRINT, t.getTokenType());
	}
	
	@Test
	public void testInputEdit() throws LexingException {
		Lexer l = new Lexer("");
		l.input = "==";
		Token t =  l.nextToken();
		assertEquals(TokenType.TOK_EQUALS, t.getTokenType());
	}
}
