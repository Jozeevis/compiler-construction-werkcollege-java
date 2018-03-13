import static org.junit.Assert.*;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenBool;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import lexer.TokenType;

import org.junit.Test;

public class LexerTest {

	@Test
	public void testEmptyString() {
		Lexer l = new Lexer("");
		assertEquals(TokenType.TOK_EOF, l.nextToken().getTokenType());
	}

	@Test
	public void testSingleDigitInteger() {
		Lexer l = new Lexer("5");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(5, ((TokenInteger) t).getValue());
	}

	@Test
	public void testMultiDigitInteger() {
		Lexer l = new Lexer("4545372");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(4545372, ((TokenInteger) t).getValue());
	}

	@Test
	public void testSinglePlus() {
		Lexer l = new Lexer("+");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_PLUS, t.getTokenType());
	}

	@Test
	public void testSingleMinus() {
		Lexer l = new Lexer("-");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MINUS, t.getTokenType());
	}

	@Test
	public void testSingleMult() {
		Lexer l = new Lexer("*");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MULT, t.getTokenType());
	}

	@Test
	public void testMultipleIntegers() {
		Lexer l = new Lexer("12 34");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_INT, t2.getTokenType());
		assertEquals(12, ((TokenInteger) t1).getValue());
		assertEquals(34, ((TokenInteger) t2).getValue());
	}

	@Test
	public void testSimpleExpression() {
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
	public void testMixedPlussesAndMinusesExpression() {
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
	public void testIdentifier() {
		Lexer l = new Lexer("missPiggy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("missPiggy", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testIdentifierWithDigits() {
		Lexer l = new Lexer("mi55Piggy23");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("mi55Piggy23", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testKeywordIf() {
		Lexer l = new Lexer("if");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_KW_IF, t.getTokenType());
	}

	@Test
	public void testKeywordPrefix() {
		Lexer l = new Lexer("iffy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
	}

	@Test
	public void testBooleanConstantTrue() {
		Lexer l = new Lexer("True");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_BOOL, t.getTokenType());
		assertEquals(true, ((TokenBool)t).getValue());
	}
}
