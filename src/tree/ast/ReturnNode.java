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
import tree.ast.types.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class ReturnNode extends ASyntaxKnot implements ITypeCheckable{
	
	public final BaseExpr returnedValue;
	
	public final FunDeclNode funDecl;

	public ReturnNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) {
		super(frontier);
		
		if  (oldKnot.children.length == 2)
			returnedValue = null;
		else
			returnedValue = ((TokenExpression) oldKnot.children[2].reduceToToken()).expression;
		
		SyntaxNode father = frontier;
		while(!(father instanceof FunDeclNode)) {
			father = father.parent;
		}
		funDecl = (FunDeclNode) father;
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		if (returnedValue == null) {
			return funDecl.funtype.returnType.equals(VoidType.instance);
		} else {
			try {
				return funDecl.funtype.returnType.equals(returnedValue.checkTypes(domain));
			} catch (TypeException | DeclarationException e) {
				e.printStackTrace();
				return false;
			}
		}
		
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// If this is not a void function, the returnvalue is on top of the stack and the old PC right under it
		// These should then be swapped
		if (returnedValue != null) {
			stack.add("swp");
		}
		// Pop the old PC from the stack and return to that point, leaving the returnvalue (if any) on top of the stack
		stack.add("ret");
	}
	
	

}
