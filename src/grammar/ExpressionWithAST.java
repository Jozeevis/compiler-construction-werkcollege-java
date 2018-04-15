package grammar;

/**
 * An expression that has a special equivallent in the syntax-tree.
 * @author Flip van Spaendonck
 */
public class ExpressionWithAST extends Expression {

	public final String id;
	
	public ExpressionWithAST(String expression, String id, ExpressionTree syntax) {
		super(expression, syntax);
		this.id = id;
	}

}
