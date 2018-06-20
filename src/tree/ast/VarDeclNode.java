/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.IDDeclarationBlock.Scope;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * An abstract syntax knot representing a variable declaration/initialization.
 * 
 * @author Flip van Spaendonck
 */
public class VarDeclNode extends ASyntaxKnot {
	/** The type of the variable **/
	public final Type type;
	/** The identifier of the variable **/
	public final String id;
	/** The TokenExpression that holds the value of the variable **/
	public final BaseExpr initialValue;
	/** The offset in the localMemory**/
	private int linkNumber;
	private Scope scope;

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
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		Type expressionType;
		if (!(expressionType = initialValue.checkTypes(domain)).matches(type)) {
			throw new TypeException("Expression was of type: "+expressionType+", while type: "+type+" was expected.");
		}
		linkNumber = domain.addIDDeclaration(id, type, scope);
		this.scope = scope;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		initialValue.addCodeToStack(stack, counter);
		switch(scope) {
		case GLOBAL:
			stack.add("ldl 1");
			stack.add("sta "+ (-linkNumber));
			break;
		case STRUCT:
			stack.add("ldl 2");
			stack.add("sta "+ (-linkNumber));
			break;
		case LOCAL:
			stack.add("stl "+(3+linkNumber));
			break;
		default:
			System.err.println("No case defined for scope: "+scope);
			break;
		}
	}

	@Override
	public boolean alwaysReturns() {
		return false;
	}

	

}
