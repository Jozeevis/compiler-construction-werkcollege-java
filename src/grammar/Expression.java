package grammar;

import java.util.LinkedList;
import java.util.List;

import lexer.Lexer;
import lexer.LexingException;
import lexer.Token;
import lexer.TokenType;

/**
 * 
 * @author Flip van Spaendonck
 *
 */
public class Expression {
	
	private static final Lexer tokenizer = new Lexer("");

	/** The array representing the expression */
	public final Object[] expression;
	/** The amount of Nodes in this expression */
	public final int nrOfNodes;
	
	public Expression(Object[] expression) {
		this.expression = expression;
		int n=0;
		for(Object o : expression) {
			if (o instanceof Node)
				n++;
		}
		nrOfNodes = n;
	}
	
	/**
	 * Parses custom expression syntax to construct an expression from the submitted String.
	 * References to nodes are done thru: ~NodeName 
	 * References to exact Strings are done thru: 'ExactWordingOfAString'
	 * References to the empty-word are done thru: null
	 * @param expression the to be parsed String
	 * @param syntax the ExpressionTree in which other nodes are stored
	 * @throws LexingException 
	 */
	public Expression(String expression, ExpressionTree syntax) throws LexingException {
		List<Object> array = new LinkedList<>();
		int front=0;
		int end=0;
		int n=0;
		while(front<expression.length()) {
			if(expression.charAt(front) == '~') {
				end = front+1;
				while(end < expression.length() && expression.charAt(end) != ' ')
					end++;
				array.add(syntax.getNode(expression.substring(front+1, end)));
				n++;
				end++;
				front = end;
			} else if(expression.charAt(front) == '.') {
				end = front+1;
				while(end < expression.length() && expression.charAt(end) != ' ')
					end++;
				array.add(new Token(TokenType.valueOf(expression.substring(front+1, end)), 0));
				n++;
				end++;
				front = end;
			} else if(expression.charAt(front) == '\'') {
				end = front+1;
				while(expression.charAt(end) != '\'')
					end++;
				tokenizer.input = expression.substring(front+1, end);
				tokenizer.currentPosition = 0;
				array.add(tokenizer.nextToken());
				end++;
				front = end;
			} else if(expression.length() >front+4 && expression.substring(front, front+4).equals("null")) {
				array.add(EmptyWord.NIL);
				front += 4;
			} else if(expression.charAt(front) == ' ') {
				front++;
			}
		}
		this.expression = array.toArray();
		nrOfNodes = n;
	}
	
	@Override
	public String toString() {
		String out = "[";
		for(Object o : expression) {
			if (o instanceof Node) {
				out+= "~"+o;
			} else if (o instanceof Token) {
				//out += "."+((Token)o).getTokenType().name();
				out += o;
			} 
			out += ",";
		}
		out += "]";
		return out;
	}
}
