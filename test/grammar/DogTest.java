package grammar;

import org.junit.Test;

import lexer.Lexer;
import parser.Parser;

public class DogTest {

	
	/*@Test
	public void smollDogTest() {
		String code = "Dog {\r\n" + 
				"	Dog () {}\r\n" + 
				"}\r\n";
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
	}*/
	@Test
	public void dogTest() {
		String code = "Dog {\r\n" + 
				"	Int nrOfBarksLeft = 5;\r\n" + 
				"	Dog() {}\r\n" + 
				"	bark0(nr) :: Int -> void {\r\n" + 
				"	    print 'b';\r\n" + 
				"		if (kaasje > 0) {bark1(nr-1);}\r\n" + 
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
				"	}\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"main () ::->void {\r\n" + 
				"	\r\n" + 
				"}";
		Lexer l = new Lexer(code);
		Parser p = new Parser(l);
	}
}
