/**
 * 
 */
package tree.ast;

import java.util.LinkedList;
import java.util.List;

import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructDeclNode extends ASyntaxKnot implements ITypeCheckable {

	public final String id;

	public final VarDeclNode[] variables;

	public final FunDeclNode[] functions;

	// ==== Constructor part ====
	public final IDDeclaration[] cArgs;
	/** The variables that are declared at the start of the constructors body **/
	public final VarDeclNode[] varDecls;
	public final SyntaxNode body;

	public StructDeclNode(SyntaxKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);

		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		List<SyntaxNode> varKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[2]);
		variables = new VarDeclNode[varKnots.size()];
		int counter = 0;
		for (SyntaxNode varDeclKnot : varKnots) {
			variables[counter++] = new VarDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
		List<SyntaxNode> funcKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[4]);
		functions = new FunDeclNode[funcKnots.size()];
		counter = 0;
		for (SyntaxNode varDeclKnot : varKnots) {
			functions[counter++] = new FunDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}

		// ==== Processing the actual constructor ====

		SyntaxKnot constructor = (SyntaxKnot) oldKnot.children[3];
		if (!((TokenIdentifier) constructor.children[0].reduceToToken()).getValue().equals(id))
			throw new Exception("Constructor has mismatching id with structure, expected id: " + id);

		
		List<SyntaxNode> varDeclKnots;
		if (constructor.children.length == 7) {
			cArgs = new IDDeclaration[] {};
			varDeclKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) constructor.children[4]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) constructor.children[5]).root;
		} else {
			List<IDDeclaration> cArgs = new LinkedList<>();
			SyntaxKnot cArgKnot = (SyntaxKnot) constructor.children[2];
			while (cArgKnot.children.length != 2) {
				cArgs.add(new IDDeclaration(Type.inferType((SyntaxExpressionKnot) cArgKnot.children[0]),
						((TokenIdentifier) cArgKnot.children[1].reduceToToken()).value));
				cArgKnot = (SyntaxKnot) cArgKnot.children[3];
			}
			cArgs.add(new IDDeclaration(Type.inferType((SyntaxExpressionKnot) cArgKnot.children[0]),
					((TokenIdentifier) cArgKnot.children[1].reduceToToken()).value));
			this.cArgs = (IDDeclaration[]) cArgs.toArray();
			varDeclKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) constructor.children[5]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) constructor.children[6]).root;
		}
		varDecls = new VarDeclNode[varDeclKnots.size()];
		counter = 0;
		for (SyntaxNode varDeclKnot : varDecls) {
			varDecls[counter++] = new VarDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.SyntaxNode#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// TODO: Make this work with overloaded functions
		// Label to jump to the function body
		// We link the variables.length so we can later save the actual variable values.
		stack.add(id + ": link " + variables.length);
		// Generate the code for the variable declarations at the beginning of the code
		// body.
		for (VarDeclNode varDeclaration : variables) {
			varDeclaration.addCodeToStack(stack, counter);
		}
		// Generate the code of the function body
		body.addCodeToStack(stack, counter);
		stack.add("ret");
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		IDDeclarationBlock block = domain;
		for (VarDeclNode varDecl : variables) {
			block = varDecl.checkTypes(block);
		}
		for (FunDeclNode funDecl : functions) {
			block = funDecl.checkTypes(block);
		}
		return block;
	}

}
