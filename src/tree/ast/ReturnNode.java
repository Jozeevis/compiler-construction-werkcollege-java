/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import tree.IDDeclaration;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class ReturnNode extends ASyntaxNode implements ITypeCheckable{
	
	public final TokenExpression returnedValue;
	
	public final FunDeclNode funDecl;

	public ReturnNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		
		if  (oldKnot.children.length == 2)
			returnedValue = null;
		else
			returnedValue = (TokenExpression) oldKnot.children[2].reduceToToken();
		
		SyntaxNode father = parent;
		while(!(father instanceof FunDeclNode)) {
			father = father.parent;
		}
		funDecl = (FunDeclNode) father;
	}

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		if (returnedValue == null) {
			return funDecl.funtype.returnType.equals(new VoidType());
		} else {
			return funDecl.funtype.returnType.equals(returnedValue.type);
		}
		
	}

}
