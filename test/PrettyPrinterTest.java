import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import lexer.Token;
import lexer.TokenInteger;
import lexer.TokenType;

import java.util.List;
import java.util.ArrayList;

import util.PrettyPrinter;

public class PrettyPrinterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInteger() {
		List<Token> tokens = new ArrayList<Token>();
		tokens.add(new TokenInteger(42));
		PrettyPrinter pp = new PrettyPrinter();
		assertEquals("42", pp.getResultString(tokens));
	}

	@Test
	public void testPlus() {
		List<Token> tokens = new ArrayList<Token>();
		tokens.add(new TokenInteger(4));
		tokens.add(new Token(TokenType.TOK_PLUS));
		tokens.add(new TokenInteger(2));
		PrettyPrinter pp = new PrettyPrinter();
		assertEquals("4 + 2", pp.getResultString(tokens));
	}

}
