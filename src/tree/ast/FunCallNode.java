/**
 * 
 */
package tree.ast;

import java.util.List;

import tree.IDDeclaration;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.FunCall;
import tree.ast.types.VoidType;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCallNode extends SyntaxNode implements ITypeCheckable{

	
	public final FunCall funCall;
	
	/**
	 * @param parent
	 */
	public FunCallNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		this.funCall = new FunCall((SyntaxKnot) oldKnot.children[0], new VoidType());
	}

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		return funCall.checkTypes(domain);
	}

}
