package grammar;

import tree.ast.ASyntaxKnot;

/**
 * @author Flip van Spaendonck
 *
 */
public class ExpressionWithAST extends Expression {

	public final String id;
	
	public ExpressionWithAST(String expression, String id, ExpressionTree syntax) {
		super(expression, syntax);
		this.id = id;
	}

}
