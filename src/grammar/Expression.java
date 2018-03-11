package grammar;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Flip van Spaendonck
 *
 */
public class Expression {

	/** The array representing the expression */
	final Object[] expression;
	
	public Expression(Object[] expression) {
		this.expression = expression;
	}
	
	/**
	 * Parses custom expression syntax to construct an expression from the submitted String.
	 * References to nodes are done thru: ~NodeName 
	 * References to exact Strings are done thru: 'ExactWordingOfAString'
	 * References to the empty-word are done thru: null
	 * @param expression the to be parsed String
	 * @param syntax the ExpressionTree in which other nodes are stored
	 */
	public Expression(String expression, ExpressionTree syntax) {
		List<Object> array = new LinkedList<>();
		int front=0;
		int end=0;
		while(front<expression.length()) {
			if(expression.charAt(front) == '~') {
				end = front+1;
				while(end < expression.length() && expression.charAt(end) != ' ')
					end++;
				array.add(syntax.getNode(expression.substring(front+1, end)));
				end++;
				front = end;
				continue;
			} else if(expression.charAt(front) == '\'') {
				end = front+1;
				while(expression.charAt(end) != '\'')
					end++;
				array.add(expression.substring(front+1, end));
				end++;
				front = end;
			} else if(expression.substring(front, front+4).equals("null")) {
				array.add(EmptyWord.NIL);
				front += 4;
			} else if(expression.charAt(front) == ' ') {
				front++;
			}
		}
		
		
		this.expression = array.toArray();
	}
}
