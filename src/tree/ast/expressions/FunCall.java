/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.Type;
import tree.ast.types.specials.FunctionType;
import tree.ast.types.BaseType;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class FunCall extends BaseExpr {
	/** The identifier for this function */
	public final String id;
	private int linkNumber;

	/** The expressions denoting the arguments for this function */
	public final BaseExpr[] arguments;
	/** The types for the arguments for this function */
	public final Type[] argumentTypes;

	public FunCall(SyntaxExpressionKnot funcall) throws IllegalThisException {
		id = ((TokenIdentifier) ((SyntaxLeaf) funcall.children[0]).leaf).value;

		if (funcall.children.length == 3) {
			arguments = new BaseExpr[0];
			argumentTypes = new Type[0];
		} else {
			int n = 0;
			SyntaxExpressionKnot currentArgument = (SyntaxExpressionKnot) funcall.children[3];
			while (currentArgument.children.length == 3) {
				currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
				n++;
			}
			arguments = new BaseExpr[n];
			argumentTypes = new Type[n];
			currentArgument = (SyntaxExpressionKnot) funcall.children[3];
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		for (BaseExpr argument : arguments)
			argument = argument.optimize();
		return this;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		FunctionType type = null;
		for (int i = 0; i < domain.block.length; i++) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(id)) {
				if (!(declaration.type instanceof FunctionType))
					throw new TypeException(
							"The id used by this function call does not correspond to a function declaration: " + id);
				linkNumber = i + 1;
				type = (FunctionType) declaration.type;
				break;
			}
		}
		if (type == null)
			throw new DeclarationException("No function with id: " + id + " has been declared.");
		if (type.inputTypes.length != arguments.length)
			throw new DeclarationException("The function declared with id: " + id + " has " + type.inputTypes.length
					+ " arguments, while " + arguments.length + " where expected.");
		for (int i = 0; i < arguments.length; i++) {
			Type argumentExpressionType;
			if (!(argumentExpressionType = arguments[i].checkTypes(domain)).equals(type.inputTypes[i]))
				throw new TypeException("Argument " + i + " was expected to have type: " + type.inputTypes[i]
						+ ", however an expression of type " + argumentExpressionType + " was used.");
		}
		return type.returnType;
	}

	/**
	 * Returns the link number for this function, which is the heap offset to the id
	 * where the last of its arguments
	 */
	public int getLinkNumber() {
		return linkNumber;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// If this is the built-in isEmpty function
		if (id == "isEmpty") {
			// If it actually has an argument
			if (arguments.length > 0) {
				// Number that will be used for all labels in this statement
				counter.incr();
				Integer count = counter.getCount();
				// Put code for the expression evaluating to the argument on the stack
				arguments[0].addCodeToStack(stack, counter);

				// Put zero on top of the stack
				stack.add("ldc 0");
				// Check if the evaluated argument is equal to zero and add the result to the
				// stack (1 for true, 0 for false)
				stack.add("eq");
				stack.add("brf ENDLABEL" + count);
				stack.add("ldc " + 0xFFFFFFFF);
				stack.add("ENDLABEL" + count + ": nop");
			}
		}
		// Otherwise this is not a built-in function
		else {
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
	}

}
