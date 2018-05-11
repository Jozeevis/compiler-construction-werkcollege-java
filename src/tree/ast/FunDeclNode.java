package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import lexer.TokenType;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.TreeProcessing;
import tree.ast.types.Type;
import tree.ast.types.VoidType;
import tree.ast.types.FunctionType;

/**
 * An abstract syntax knot representing a function declaration.
 * @author Lars Kuijpers
 */
public class FunDeclNode extends ASyntaxKnot implements ICodeBlock {
	
	/** The identifier of the function **/
	public final String id;
	/** The identifiers of the arguments of the function **/
	public final String[] funargs = {};
	/** The type of the function **/
	public final FunctionType funtype;
	/** The variables that are declared at the start of the function body **/
	public final VarDeclNode[] varDecls;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;
	
	public FunDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);

		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		if (oldKnot.children.length == 4) { // Function declaration without Function arguments
			//"~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			funtype = ExtractFunctionType((SyntaxExpressionKnot)oldKnot.children[1]);
			varDecls = ExtractVariables((SyntaxExpressionKnot)oldKnot.children[2]);
			body = oldKnot.children[3];
		}
		else { // Function declaration with Function arguments
			//"~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			// Get identifiers of the function arguments out of the FArgs expression
			for (int i = 0; i<((SyntaxExpressionKnot)oldKnot.children[1]).expression.nrOfNodes; i++) {
				Object o = ((SyntaxExpressionKnot)oldKnot.children[1]).expression.expression[i];
				if (o instanceof TokenIdentifier)
					funargs[i] = ((TokenIdentifier)o).getValue();
			}
			funtype = ExtractFunctionType((SyntaxExpressionKnot)oldKnot.children[2]);
			varDecls = ExtractVariables((SyntaxExpressionKnot)oldKnot.children[3]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[4]).root;
		}
	}
	
	/**
	 * Extracts the FunctionType from the given SyntaxKnot and returns it
	 */
	private FunctionType ExtractFunctionType(SyntaxExpressionKnot funtype) {
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
					left[leftCounter] = Type.inferType((SyntaxExpressionKnot)o);
					leftCounter++;
				}
				// Otherwise it is the return side
				else {
					right = Type.inferType((SyntaxExpressionKnot)o);
				}
			}
		}
		
		return new FunctionType(left, right);
	}
	
	/**
	 * Extracts all variable declarations out of the given syntax knot and returns them as an array
	 */
	private static VarDeclNode[] ExtractVariables(SyntaxExpressionKnot vardecls) {
		VarDeclNode[] variables = {};
		int counter = 0;
		SyntaxExpressionKnot currentKnot = vardecls;
		while (currentKnot.children.length == 2) {
			variables[counter] = new VarDeclNode((SyntaxExpressionKnot)currentKnot.children[0], currentKnot);
			counter++;
			currentKnot = (SyntaxExpressionKnot)currentKnot.children[1];
		}
		return variables;
	}

	/**
	 * @author Flip van Spaendonck
	 */
	@Override
	public IDDeclaration[] getBlock(){
		IDDeclaration[] out = new IDDeclaration[varDecls.length+1];
		out[0] = new IDDeclaration(funtype,id);
		for(int i=0; i< varDecls.length; i++) {
			out[i+1] = varDecls[i].getDeclaration();
		}
		return out;
	}

	/**
	 * @author Flip van Spaendonck
	 */
	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[] {body};
	}

}
