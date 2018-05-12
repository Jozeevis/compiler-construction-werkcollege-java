/**
 * 
 */
package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a variable declaration/initialization.
 * 
 * @author Flip van Spaendonck
 */
public class VarDeclNode extends ASyntaxKnot implements ICodeBlock, ITypeCheckable {
	/** The type of the variable **/
	public final Type type;
	/** The identifier of the variable **/
	public final String id;
	/** The TokenExpression that holds the value of the variable **/
	public final TokenExpression body;
	/** The offset in the localMemory**/
	private int linkNumber;

	public VarDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		type = Type.inferType((SyntaxExpressionKnot) oldKnot.children[0]);
		id = ((TokenIdentifier) oldKnot.children[1].reduceToToken()).getValue();
		body = (TokenExpression) oldKnot.children[3].reduceToToken();
	}


	@Override
	public IDDeclarationBlock getBlock(IDDeclarationBlock previous) {
		IDDeclarationBlock newBlock = new IDDeclarationBlock(previous, new IDDeclaration(type, id));
		for(int i=newBlock.block.length-1; i>=0; i--) {
			IDDeclaration declaration = newBlock.block[i];
			if (declaration.id.equals(id)) {
				linkNumber = i + 1;
			}
		}
		return newBlock;
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return body.expression.checkTypes(domain);
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		body.expression.addCodeToStack(stack);
		stack.add("ldl "+linkNumber);
		stack.add("sth 0");
	}

	

}
