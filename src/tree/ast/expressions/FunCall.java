/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.FunDeclaration;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
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

	/** The expressions denoting the arguments for this function */
	public final BaseExpr[] arguments;
	/** The types for the arguments for this function */
	public final Type[] argumentTypes;

	private String branchAddress;
	
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
		FunDeclaration funDecl = domain.findFunDeclaration(id);
		return checkTypes(funDecl, domain);
	}
	
	public Type checkTypes(FunDeclaration funDecl, IDDeclarationBlock domain) throws TypeException, DeclarationException {
		FunctionType type = funDecl.type;
		branchAddress = funDecl.branchAddress;
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

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		for(BaseExpr argument : arguments) {
			argument.addCodeToStack(stack, counter);
			stack.add("sth");
		}
		stack.add("bsr " + branchAddress);
	}

}
