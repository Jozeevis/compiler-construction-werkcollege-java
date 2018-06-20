/**
 * 
 */
package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.IDDeclarationBlock.Scope;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.ast.accessors.Accessor;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.IllegalThisException;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class AssignmentNode extends ASyntaxKnot {

	public final String id;
	public final Accessor[] accessors;
	public final BaseExpr expression;
	
	private int linkNumber;
	private Scope scope;
	
	public AssignmentNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws IllegalThisException {
		super(frontier);
		id = ((TokenIdentifier)oldKnot.children[0].reduceToToken()).value;
		accessors = TreeProcessing.processFieldStar((SyntaxExpressionKnot) oldKnot.children[1]);
		expression = ((TokenExpression)oldKnot.children[3].reduceToToken()).expression;
	}
	

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		Type expectedType = expression.checkTypes(domain);
		IDDeclaration varDef = domain.findIDDeclaration(id);
		Type innerType = varDef.type;
		linkNumber = varDef.offset;
		this.scope = varDef.scope;
		for(Accessor accessor : accessors) {
			innerType = accessor.checkTypes(domain, innerType);
		}
		if (!expectedType.equals(innerType))
			throw new TypeException("Expression was of Type: "+expectedType + ", while type "+innerType+" was expected.");
	}

	public int getLinkNumber() {
		return linkNumber;
	}


	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Generate code for the assignment body
		expression.addCodeToStack(stack, counter);
		// Save the result in the heap address given by the linknumber
		switch(scope) {
		case GLOBAL:
			stack.add("ldl 1");
			break;
		case STRUCT:
			stack.add("ldl 2");
			break;
		case LOCAL:
			
			break;
		default:
			System.err.println("No case defined for scope: "+scope);
			break;
		}
		//System.out.println("kaasplankje:"+ accessors.length);
		for(Accessor accessor : accessors) {
			accessor.addCodeToStack(stack, null);
		}
		if (scope == Scope.LOCAL)
			stack.add("stl "+(3+linkNumber));
		else
			stack.add("sta "+ (-linkNumber));
	}


	@Override
	public boolean alwaysReturns() {
		return false;
	}



}
