/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.SyntaxExpressionKnot;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.accessors.Accessor;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class CallUp extends OneArg {
	
	private final Accessor[] accessors;

	public CallUp(BaseExpr expr, SyntaxExpressionKnot fieldStar) {
		super(expr);
		accessors = TreeProcessing.processFieldStar(fieldStar);	
		}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		val = val.optimize();
		return this;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		val.addCodeToStack(stack, counter);
		for (Accessor accessor : accessors) {
			accessor.addCodeToStack(stack);
		}
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#checkTypes(tree.ast.IDDeclarationBlock)
	 */
	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type innerType = val.checkTypes(domain);
		for(Accessor accessor : accessors) {
			innerType = accessor.checkTypes(domain, innerType);
		}
		return innerType;
	}

}
