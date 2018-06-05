package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.Token;
import lexer.TokenExpression;
import lexer.TokenIdentifier;
import lexer.TokenType;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.CodeGenerator;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;
import tree.ast.types.specials.FunctionType;
import tree.ast.types.specials.VoidType;

/**
 * An abstract syntax knot representing a function declaration.
 * 
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class FunDeclNode extends ASyntaxKnot implements ITypeCheckable{

	/** The identifier of the function **/
	public final String id;
	/** The identifiers of the arguments of the function **/
	public final IDDeclaration[] funArgs;
	/** The type of the function **/
	public final FunctionType funtype;
	/** The variables that are declared at the start of the function body **/
	public final VarDeclNode[] varDecls;
	/** The size with which a link should be made **/
	private int linkSize;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;

	public FunDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);

		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		if (oldKnot.children.length == 8) { // Function declaration without Function arguments
			// "~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			funArgs = new IDDeclaration[0];
			funtype = ExtractFunctionType((SyntaxExpressionKnot) oldKnot.children[4]);
			varDecls = ExtractVariables((SyntaxExpressionKnot) oldKnot.children[6]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[7]).root;
		} else { // Function declaration with Function arguments
					// "~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
					// Get identifiers of the function arguments out of the FArgs expression
			funtype = ExtractFunctionType((SyntaxExpressionKnot) oldKnot.children[5]);
			funArgs = new IDDeclaration[funtype.inputTypes.length];
			SyntaxExpressionKnot fArgKnot = (SyntaxExpressionKnot) oldKnot.children[2];
			int i = 0;
			while (fArgKnot.children.length != 1) {
				funArgs[i] = new IDDeclaration(funtype.inputTypes[i],
						((TokenIdentifier) fArgKnot.children[0].reduceToToken()).value);
				fArgKnot = (SyntaxExpressionKnot) fArgKnot.children[2];
				i++;
			}
			funArgs[i] = new IDDeclaration(funtype.inputTypes[i],
					((TokenIdentifier) fArgKnot.children[0].reduceToToken()).value);
			varDecls = ExtractVariables((SyntaxExpressionKnot) oldKnot.children[7]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) oldKnot.children[8]).root;
		}
		
		children = new SyntaxNode[] {body};
	}

	/**
	 * Extracts all variable declarations out of the given syntax knot and returns
	 * them as an array
	 */
	private static VarDeclNode[] ExtractVariables(SyntaxExpressionKnot vardecls) {
		VarDeclNode[] variables = {};
		int counter = 0;
		SyntaxExpressionKnot currentKnot = vardecls;
		while (currentKnot.children.length == 2) {
			variables[counter] = new VarDeclNode((SyntaxExpressionKnot) currentKnot.children[0], currentKnot);
			counter++;
			currentKnot = (SyntaxExpressionKnot) currentKnot.children[1];
		}
		return variables;
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
						right = VoidType.instance;
					}
				}
			} else {
				// If we haven't had the '->' yet, add the Type to the left side
				if (in == true) {
					left[leftCounter] = Type.inferType((SyntaxExpressionKnot) o);
					leftCounter++;
				}
				// Otherwise it is the return side
				else {
					right = Type.inferType((SyntaxExpressionKnot) o);
				}
			}
		}

		return new FunctionType(left, right);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// TODO: Make this work with overloaded functions
		// Label to jump to the function body
		stack.add(id + ": link "+linkSize);
		// Generate the code for the variable declarations at the beginning of the code body.
		for(VarDeclNode varDeclaration : varDecls) {
			varDeclaration.addCodeToStack(stack, counter);
		}
		// Generate the code of the function body
		body.addCodeToStack(stack, counter);
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		IDDeclaration[] block = new IDDeclaration[funArgs.length + varDecls.length + 1];
		block[0] = new IDDeclaration(funtype, id);
		for (int i = 0; i < funArgs.length; i++) {
			block[i + 1] = funArgs[i];
		}
		for (int i = 0; i < varDecls.length; i++) {
			block[funArgs.length + i + 1] = new IDDeclaration(varDecls[i].type, varDecls[i].id);
		}
		IDDeclarationBlock out = new IDDeclarationBlock(domain, block);
		linkSize = out.block.length;
		return out;
	}

}
