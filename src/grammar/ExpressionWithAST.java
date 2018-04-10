package grammar;

import tree.ASyntaxKnot;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class ExpressionWithAST extends Expression {

	public ExpressionWithAST(Object[] expression) {
		super(expression);
		// TODO Auto-generated constructor stub
	}
	
	public abstract ASyntaxKnot toAST();

}
