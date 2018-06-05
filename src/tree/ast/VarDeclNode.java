/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a variable declaration/initialization.
 * 
 * @author Flip van Spaendonck
 */
public class VarDeclNode extends ASyntaxKnot implements ITypeCheckable {
	/** The type of the variable **/
	public final Type type;
	/** The identifier of the variable **/
	public final String id;
	/** The TokenExpression that holds the value of the variable **/
	public final BaseExpr initialValue;
	/** The offset in the localMemory**/
	private int linkNumber;

	public VarDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		type = Type.inferType((SyntaxExpressionKnot) oldKnot.children[0]);
		id = ((TokenIdentifier) oldKnot.children[1].reduceToToken()).getValue();
		if (oldKnot.children.length == 2)
			initialValue = type.getNullValue();
		else
			initialValue = ((TokenExpression) oldKnot.children[3].reduceToToken()).expression;
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType;
		if (!(expressionType = initialValue.checkTypes(domain)).equals(type)) {
			throw new TypeException("Expression was of type: "+expressionType+", while type: "+type+" was expected.");
		}
		IDDeclarationBlock newBlock = new IDDeclarationBlock(domain, new IDDeclaration(type, id));
		for(int i=newBlock.block.length-1; i>=0; i--) {
			IDDeclaration declaration = newBlock.block[i];
			if (declaration.id.equals(id)) {
				linkNumber = i + 1;
			}
		}
		return newBlock;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		initialValue.addCodeToStack(stack, counter);
		stack.add("ldl "+linkNumber);
		stack.add("sth 0");
	}

	

}
