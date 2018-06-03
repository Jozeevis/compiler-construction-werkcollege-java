/**
 * 
 */
package tree.ast.expressions.structs;

import java.util.List;

import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class InitExpr extends BaseExpr {

	/** The identifier for this function */
	public final String id;
	private int linkNumber;
	
	/** The expressions denoting the arguments for this function */
	public final BaseExpr[] arguments;
	
	public InitExpr(SyntaxExpressionKnot initCall) {
		id = ((TokenIdentifier) ((SyntaxLeaf) initCall.children[1]).leaf).value;

		if (initCall.children.length == 4) {
			arguments = new BaseExpr[0];
		} else {
			int n = 0;
			SyntaxExpressionKnot currentArgument = (SyntaxExpressionKnot) initCall.children[3];
			while (currentArgument.children.length == 3) {
				currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
				n++;
			}
			arguments = new BaseExpr[n];
			currentArgument = (SyntaxExpressionKnot) initCall.children[4];
			int i = 0;
			while (true) {
				arguments[i] = BaseExpr.convertToExpr((SyntaxExpressionKnot) currentArgument.children[0]);
				if (currentArgument.children.length == 3) {
					currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
					i++;
				} else {
					break;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		for (BaseExpr argument : arguments)
			argument = argument.optimize();
		return this;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		
		//TODO: load the local variables.
		
		// Follow a function call, evaluating its arguments and saving them as local
		// variables and jumping to the label of the function itself.
		// If the function has any arguments
		if (arguments.length > 0) {
			// Make space on the stack for these arguments
			stack.add("link " + arguments.length);
			// For every argument
			for (int i = 0; i < this.arguments.length; i++) {
				// Add the code to evaluate the argument
				arguments[i].addCodeToStack(stack, counter);
				// Save the result to the space freed by the link function
				stack.add("stl " + i);
			}
		}
		// Jump to the label of the function where the function code will be executed
		// TODO: currently doesn't work for overloaded functions (should be fixed in the
		// declaration and then use the right id here)
		stack.add("bsr " + id);
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#checkTypes(tree.ast.IDDeclarationBlock)
	 */
	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		// TODO Auto-generated method stub
		return null;
	}

}
