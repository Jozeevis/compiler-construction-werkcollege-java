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
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.ListType;
import tree.ast.types.TupleType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class AssignmentNode extends ASyntaxKnot implements ITypeCheckable{

	public final String id;
	private int linkNumber;
	public final TokenField[] accessors;
	public final BaseExpr expression;
	
	public AssignmentNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) {
		super(frontier);
		
		id = ((TokenIdentifier)oldKnot.children[0].reduceToToken()).value;
		accessors = extractFieldTokens((SyntaxExpressionKnot) oldKnot.children[1]);
		expression = ((TokenExpression)oldKnot.children[0].reduceToToken()).expression;
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
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
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
			throw new DeclarationException("No variable with id: "+id+ " is currently defined.");
		
		Type expectedType = varType;
		for(TokenField accessor : accessors) {
			if (accessor instanceof TokenTupleFunction) {
				if (expectedType instanceof TupleType) {
					if (((TokenTupleFunction) accessor).type == TupleFunction.TUPLEFUNC_FIRST) 
						expectedType = ((TupleType) expectedType).leftType;
					else
						expectedType = ((TupleType) expectedType).rightType;
				} else {
					throw new TypeException("Expression was of type: "+expectedType+" while type TupleType was expected.");
				}
			} else if (accessor instanceof TokenListFunction) {
				if (expectedType instanceof ListType) {
					if (((TokenListFunction)accessor).type == ListFunction.LISTFUNC_HEAD) 
						expectedType = ((ListType) expectedType).listedType;
					else
						;//ExpectedType stays the same when continuing in the list
				} else {
					throw new TypeException("Expression was of type: "+expectedType+" while type ListType was expected.");
				}
			} else
				throw new DeclarationException("The assignmentNode has no option declared for accessor: " +accessor);
		}
		expression.checkTypes(domain).equals(expectedType);
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
		for(TokenField accessor : accessors) {
			accessor.addCodeToStack(stack);
		}
		stack.add("sth 0");
	}



}
