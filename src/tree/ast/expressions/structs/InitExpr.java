/**
 * 
 */
package tree.ast.expressions.structs;

import java.util.List;

import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.StructDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.IllegalThisException;
import tree.ast.types.CustomType;
import tree.ast.types.StructType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class InitExpr extends BaseExpr {

	/** The identifier for this function */
	public final String id;
	public String branchName;

	/** The expressions denoting the arguments for this function */
	public final BaseExpr[] arguments;
	private String branchAddress;

	public InitExpr(SyntaxExpressionKnot initCall) throws IllegalThisException {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.ast.expressions.BaseExpr#checkTypes(tree.ast.IDDeclarationBlock)
	 */
	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		StructDeclaration structDecl = domain.findStructDeclaration(id);
		branchAddress = structDecl.constructorBranchAddress;
		if (arguments.length != structDecl.constructorArgumentTypes.length) {
			throw new DeclarationException(
					"The constructor declared with id: " + id + " has " + structDecl.constructorArgumentTypes.length
							+ " arguments, while " + arguments.length + " where expected.");
		}
		for (int i = 0; i < arguments.length; i++) {
			Type type;
			if (!(type = arguments[i].checkTypes(domain)).matches(structDecl.constructorArgumentTypes[i])) {
				throw new TypeException(
						"Argument " + i + " was expected to have type: " + structDecl.constructorArgumentTypes[i]
								+ ", however an expression of type " + type + " was used.");
			}
		}
		return structDecl.structType;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {

		for (BaseExpr argument : arguments) {
			argument.addCodeToStack(stack, counter);
			stack.add("sth\n");
		}
		stack.add("stmh 0 "+arguments.length + "\n");
		stack.add("str 5\n");
		stack.add("bsr " + branchAddress + "\n");
	}

}
