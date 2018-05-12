/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import lexer.TokenIdentifier;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.ast.IDDeclarationBlock;
import tree.ast.types.FunctionType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class FunCall extends BaseExpr {
	
	public final Type expectedType;
	public final String id;
	private int linkNumber;
	
	public final BaseExpr[] arguments;
	public final Type[] argumentTypes;

	public FunCall(SyntaxExpressionKnot funcall, Type expectedType) {
		this.expectedType = expectedType;
		id = ((TokenIdentifier)((SyntaxLeaf)funcall.children[0]).leaf).value;
		
		if (funcall.children.length == 3) {
			arguments = new BaseExpr[0];
			argumentTypes = new Type[0];
		} else {
			int n=0;
			SyntaxExpressionKnot currentArgument = (SyntaxExpressionKnot) funcall.children[3];
			while(currentArgument.children.length == 3) {
				currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
				n++;
			}
			arguments = new BaseExpr[n];
			argumentTypes = new Type[n];
			currentArgument = (SyntaxExpressionKnot) funcall.children[3];
			int i = 0;
			while(true) {
				arguments[i] = BaseExpr.convertToExpr((SyntaxExpressionKnot) currentArgument.children[0]);
				argumentTypes[i] = Type.inferExpressionType((SyntaxExpressionKnot) currentArgument.children[0]);
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
		for(BaseExpr argument : arguments)
			argument.optimize();
		return this;
	}
	
	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		FunctionType type = null;
		for(int i=0; i<domain.block.length; i++) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(id)) {
				if (!(declaration.type instanceof FunctionType))
					return false;
				// TODO: Flip can you explain to me what this does please
				linkNumber = i + 1;
				type = (FunctionType) declaration.type;
				break;
			}
		}
		if (type == null)
			return false;
		if (!type.returnType.equals(expectedType))
			return false;
		if (type.inputTypes.length != arguments.length)
			return false;
		for(int i=0; i<arguments.length; i++) {
			if (!argumentTypes[i].equals(type.inputTypes[i]))
				return false;
			if (!arguments[i].checkTypes(domain))
				return false;
		}
		return false;
	}

	/**
	 * Returns the link number for this function, which is the heap offset to the id where the last of its arguments
	 */
	public int getLinkNumber() {
		return linkNumber;
	}

	@Override
	public void addCodeToStack(List<String> stack) {
		// Follow a function call, evaluating its arguments and saving them as local variables and jumping to the label of the function itself.
		// If the function has any arguments
		if (arguments.length > 0) {
			// Make space on the stack for these arguments
			stack.add("link " + arguments.length);
			// For every argument
			for (int i = 0; i < this.arguments.length; i++) {
				// Add the code to evaluate the argument
				arguments[i].addCodeToStack(stack);
				// Save the result to the space freed by the link function
				stack.add("stl " + i);
			}
		}
		// Jump to the label of the function where the function code will be executed
		// TODO: currently doesn't work for overloaded functions (should probably be fixed in function declarations rather than here)
		stack.add("bsr " + id);
	}

}
