package grammar;

import lexer.LexingException;

/**
 * An expression that has a special equivallent in the syntax-tree.
 * @author Flip van Spaendonck
 */
public class ExpressionWithAST extends Expression {

	public final String id;
	
	public ExpressionWithAST(String expression, String id, ExpressionTree syntax) throws LexingException {
		super(expression, syntax);
		this.id = id;
	}

}
