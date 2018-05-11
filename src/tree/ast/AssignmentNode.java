/**
 * 
 */
package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.ListFunction;
import lexer.TokenExpression;
import lexer.TokenField;
import lexer.TokenIdentifier;
import lexer.TokenListFunction;
import lexer.TokenTupleFunction;
import lexer.TupleFunction;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.ListType;
import tree.ast.types.TupleType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class AssignmentNode extends ASyntaxKnot implements ITypeCheckable{

	public final String id;
	private int linkNumber;
	public final TokenField[] accessors;
	public final TokenExpression expression;
	
	public AssignmentNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) {
		super(frontier);
		
		id = ((TokenIdentifier)oldKnot.children[0].reduceToToken()).value;
		accessors = extractFieldTokens((SyntaxExpressionKnot) oldKnot.children[1]);
		expression = (TokenExpression)oldKnot.children[0].reduceToToken();
	}
	

	private static TokenField[] extractFieldTokens(SyntaxExpressionKnot fieldStar) {
		List<TokenField> tokens = new LinkedList<>(); 
		SyntaxExpressionKnot currentKnot = fieldStar;
		while (currentKnot.children.length == 2) {
			tokens.add((TokenField) currentKnot.children[0].reduceToToken());
			currentKnot = (SyntaxExpressionKnot)currentKnot.children[1];
		}
		return (TokenField[]) tokens.toArray();
	}

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		Type varType = null;
		for(int i=domain.block.length-1; i>=0; i--) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(id)) {
				varType = declaration.type;
				linkNumber = i + 1;
				break;
			}
		}
		if (varType == null)
			return false;
		
		Type expectedType = varType;
		for(TokenField accessor : accessors) {
			if (accessor instanceof TokenTupleFunction) {
				if (expectedType instanceof TupleType) {
					if (((TokenTupleFunction) accessor).type == TupleFunction.TUPLEFUNC_FIRST) 
						expectedType = ((TupleType) expectedType).leftType;
					else
						expectedType = ((TupleType) expectedType).rightType;
				} else {
					return false;
				}
			} else if (accessor instanceof TokenListFunction) {
				if (expectedType instanceof ListType) {
					if (((TokenListFunction)accessor).type == ListFunction.LISTFUNC_HEAD) 
						expectedType = ((ListType) expectedType).listedType;
					else
						;//ExpectedType stays the same when continuing in the list
				} else {
					return false;
				}
			} else
				return false;
		}
		return expectedType.equals(expression.type);
	}


	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[0];
	}

}
