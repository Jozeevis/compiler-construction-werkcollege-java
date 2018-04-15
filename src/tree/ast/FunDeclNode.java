package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import lexer.TokenType;
import tree.IDDeclaration;
import tree.SyntaxKnot;
import tree.ast.types.Type;
import tree.ast.types.VoidType;
import tree.ast.types.FunctionType;

/**
 * An abstract syntax knot representing a function declaration.
 * @author Lars Kuijpers
 */
public class FunDeclNode extends ASyntaxNode implements IDeclarable, ITypeCheckable {
	
	/** The identifier of the function **/
	public final String id;
	/** The identifiers of the arguments of the function **/
	public final String[] funargs = {};
	/** The type of the function **/
	public final FunctionType funtype;
	/** The variables that are declared at the start of the function body **/
	public final VarDeclNode[] vardecls;
	/** The TokenExpression that denotes the function body **/
	public final TokenExpression body;
	
	public FunDeclNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);

		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		if (oldKnot.children.length == 4) { // Function declaration without Function arguments
			//"~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			funtype = ExtractFunctionType((SyntaxKnot)oldKnot.children[1]);
			vardecls = ExtractVariables((SyntaxKnot)oldKnot.children[2]);
			body = (TokenExpression) oldKnot.children[3].reduceToToken();
		}
		else { // Function declaration with Function arguments
			//"~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			// Get identifiers of the function arguments out of the FArgs expression
			for (int i = 0; i<((SyntaxKnot)oldKnot.children[1]).expression.nrOfNodes; i++) {
				Object o = ((SyntaxKnot)oldKnot.children[1]).expression.expression[i];
				if (o instanceof TokenIdentifier)
					funargs[i] = ((TokenIdentifier)o).getValue();
			}
			funtype = ExtractFunctionType((SyntaxKnot)oldKnot.children[2]);
			vardecls = ExtractVariables((SyntaxKnot)oldKnot.children[3]);
			body = (TokenExpression) oldKnot.children[4].reduceToToken();
		}
	}
	
	/**
	 * Extracts the FunctionType from the given SyntaxKnot and returns it
	 */
	private FunctionType ExtractFunctionType(SyntaxKnot funtype) {
		Type[] left = {};
		int leftCounter = 0;
		Type right = null;
		boolean in = true;
		
		for (int i = 0; i < funtype.expression.nrOfNodes; i++) {
			Object o = funtype.expression.expression[i];
			// Checks if the current object is the '->' token
			if (o instanceof Token) {
				if (((Token) o).getTokenType() == TokenType.TOK_MAPSTO) {
					in = false;
				}
				// Otherwise it might be the void token
				else {
					if (((Token) o).getTokenType() == TokenType.TOK_KW_VOID) {
						right = new VoidType();
					}
				}
			}
			else {
				// If we haven't had the '->' yet, add the Type to the left side
				if (in == true) {
					left[leftCounter] = Type.inferType((SyntaxKnot)o);
					leftCounter++;
				}
				// Otherwise it is the return side
				else {
					right = Type.inferType((SyntaxKnot)o);
				}
			}
		}
		
		return new FunctionType(left, right);
	}
	
	/**
	 * Extracts all variable declarations out of the given syntax knot and returns them as an array
	 */
	private static VarDeclNode[] ExtractVariables(SyntaxKnot vardecls) {
		VarDeclNode[] variables = {};
		int counter = 0;
		SyntaxKnot currentKnot = vardecls;
		while (currentKnot.children.length == 2) {
			variables[counter] = new VarDeclNode((SyntaxKnot)currentKnot.children[1], currentKnot);
			counter++;
			currentKnot = (SyntaxKnot)currentKnot.children[2];
		}
		return variables;
	}

	/**
	 * @author Flip van Spaendonck
	 */
	@Override
	public IDDeclaration getDeclaration() {
		return new IDDeclaration(funtype,id);
	}

	/**
	 * @author Flip van Spaendonck
	 */
	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		List<IDDeclaration> newDomain = new LinkedList<>(domain);
		for(VarDeclNode varDecl :  vardecls) {
			newDomain.add(varDecl.getDeclaration());
		}
		return body.expression.checkTypes(newDomain);
	}

}
