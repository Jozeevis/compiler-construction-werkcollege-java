/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import processing.DeclarationException;
import processing.TypeException;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;
import tree.ast.types.specials.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class ReturnNode extends ASyntaxKnot implements ITypeCheckable {

	public final BaseExpr returnedValue;

	public final FunDeclNode funDecl;

	public ReturnNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws IllegalReturnException {
		super(frontier);

		if (oldKnot.children.length == 2)
			returnedValue = null;
		else
			returnedValue = ((TokenExpression) oldKnot.children[2].reduceToToken()).expression;

		SyntaxNode father = frontier;
		while (!(father instanceof FunDeclNode)) {
			father = father.parent;
			if (father == null)
				throw new IllegalReturnException();
		}
		funDecl = (FunDeclNode) father;
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		if (returnedValue == null) {
			if (funDecl.funtype.returnType.equals(VoidType.instance))
				return domain;
			else
				throw new TypeException("Function: " + funDecl.id + " should return nothing.");
		} else {
			Type returnedType;
			if (funDecl.funtype.returnType.equals(returnedType = returnedValue.checkTypes(domain)))
				return domain;
			else
				throw new TypeException("Function: " + funDecl.id + " should return type: " + funDecl.funtype.returnType
						+ " while Type: " + returnedType + "was returned.");
		}

	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// If this is not a void function, the returnvalue is on top of the stack and
		// the old PC right under it
		// These should then be swapped
		if (returnedValue != null) {
			stack.add("swp");
		}
		// Pop the old PC from the stack and return to that point, leaving the
		// returnvalue (if any) on top of the stack
		stack.add("ret");
	}

}
