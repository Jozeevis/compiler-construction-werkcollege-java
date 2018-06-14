/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.SyntaxNode;
import tree.ast.FunDeclNode;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.StructDeclNode;
import tree.ast.types.CustomType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class This extends NoArg {
	
	private StructDeclNode structDecl;

	public This(SyntaxNode frontier) throws IllegalThisException {
		SyntaxNode father = frontier;
		while (!(father instanceof StructDeclNode)) {
			father = father.parent;
			if (father == null)
				throw new IllegalThisException();
		}
		structDecl = (StructDeclNode) father;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldl 1");

	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#checkTypes(tree.ast.IDDeclarationBlock)
	 */
	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return new CustomType(structDecl.id);
	}

}
