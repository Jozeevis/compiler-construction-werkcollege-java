/**
 * 
 */
package tree.ast.expressions;

import java.util.LinkedList;
import java.util.List;

import lexer.PrimitiveType;
import lexer.TokenExpression;
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
import tree.ast.types.ListType;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class FunCall extends BaseExpr {
	/** The identifier for this function */
	public final String id;

	/** The expressions denoting the arguments for this function */
	public final BaseExpr[] arguments;
	
	private Type[] argumentTypes;

	private String branchAddress;
	
	public FunCall(SyntaxExpressionKnot funcall) throws IllegalThisException {
		System.out.println(funcall.expression);
		id = ((TokenIdentifier) ((SyntaxLeaf) funcall.children[0]).leaf).value;

		if (funcall.children.length == 3) {
			arguments = new BaseExpr[0];
		} else {
			SyntaxExpressionKnot currentArgument = (SyntaxExpressionKnot) funcall.children[2];
			List<BaseExpr> arguments = new LinkedList<>();
			System.out.println(currentArgument);
			while (currentArgument.children.length == 3) {
				arguments.add(((TokenExpression) currentArgument.children[0].reduceToToken()).expression);
				currentArgument = (SyntaxExpressionKnot) currentArgument.children[2];
			}
			arguments.add(((TokenExpression) currentArgument.children[0].reduceToToken()).expression);
			this.arguments = new BaseExpr[arguments.size()];
			for(int i=0; i< this.arguments.length; i++) {
				this.arguments[i] = arguments.get(i);
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
		argumentTypes = new Type[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			Type argumentExpressionType;
			if (!(argumentExpressionType = arguments[i].checkTypes(domain)).equals(type.inputTypes[i]))
				throw new TypeException("Argument " + i + " was expected to have type: " + type.inputTypes[i]
						+ ", however an expression of type " + argumentExpressionType + " was used.");
			argumentTypes[i] = argumentExpressionType;
		}
		return type.returnType;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		for(int i = 0; i < arguments.length; i++) {
			BaseExpr argument = arguments[i];
			argument.addCodeToStack(stack, counter);
			if (!(argumentTypes[i] instanceof ListType))
				stack.add("sth");
			else
				stack.add("ajs -1");
		}
		stack.add("stmh "+arguments.length);
		stack.add("str 5");
		stack.add("bsr " + branchAddress);
	}

}
