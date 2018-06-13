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
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.ast.accessors.Accessor;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class AssignmentNode extends ASyntaxKnot implements ITypeCheckable{

	public final String id;
	private int linkNumber;
	public final Accessor[] accessors;
	public final BaseExpr expression;
	
	public AssignmentNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) {
		super(frontier);
		
		id = ((TokenIdentifier)oldKnot.children[0].reduceToToken()).value;
		accessors = TreeProcessing.processFieldStar((SyntaxExpressionKnot) oldKnot.children[1]);
		expression = ((TokenExpression)oldKnot.children[0].reduceToToken()).expression;
	}
	

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expectedType = expression.checkTypes(domain);
		Type innerType = null;
		for(int i=domain.block.length-1; i>=0; i--) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(id)) {
				innerType = declaration.type;
				linkNumber = i + 1;
				break;
			}
		}
		if (innerType == null)
			throw new DeclarationException("No variable with id: "+id+ " is currently defined.");
		for(Accessor accessor : accessors) {
			innerType = accessor.checkTypes(domain, innerType);
		}
		if (!expectedType.equals(innerType))
			throw new TypeException("Expression was of Type: "+expectedType + ", while type "+innerType+" was expected.");
		return domain;
	}

	public int getLinkNumber() {
		return linkNumber;
	}


	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Generate code for the assignment body
		expression.addCodeToStack(stack, counter);
		// Save the result in the heap address given by the linknumber
		stack.add("ldl" + linkNumber);
		for(Accessor accessor : accessors) {
			accessor.addCodeToStack(stack);
		}
		stack.add("sth 0");
	}



}
