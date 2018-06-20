/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import lexer.PrimitiveType;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.ast.LabelCounter;
import tree.ast.accessors.Accessor;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class CallUp extends OneArg {
	
	private final Accessor[] accessors;
	private Type finalType;

	public CallUp(BaseExpr expr, SyntaxExpressionKnot fieldStar) throws IllegalThisException {
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
			accessor.addCodeToStack(stack, counter);
		}
		//if (finalType instanceof BaseType)
		//	stack.add("ldh 0");
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
		finalType = innerType;
		return innerType;
	}

}
