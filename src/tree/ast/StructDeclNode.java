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
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.IDDeclarationBlock.Scope;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructDeclNode extends ASyntaxKnot {

	public final String id;
	public final VarDeclNode[] attributes;

	public final FunDeclNode[] functions;

	// ==== Constructor part ====
	public final String[] cArgs;
	public final Type[] cArgTypes;
	/** The variables that are declared at the start of the constructors body **/
	public final VarDeclNode[] varDecls;
	public final SyntaxNode body;
	/** The label used in the SSM code */
	private String branchAddress;

	public StructDeclNode(SyntaxKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);

		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		List<SyntaxNode> varKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[2]);
		attributes = new VarDeclNode[varKnots.size()];
		int counter = 0;
		for (SyntaxNode varDeclKnot : varKnots) {
			attributes[counter++] = new VarDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
		List<SyntaxNode> funcKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[4]);
		functions = new FunDeclNode[funcKnots.size()];
		counter = 0;
		for (SyntaxNode funcDeclKnot : funcKnots) {
			functions[counter++] = new FunDeclNode((SyntaxExpressionKnot) funcDeclKnot, frontier);
		}

		// ==== Processing the actual constructor ====

		SyntaxKnot constructor = (SyntaxKnot) oldKnot.children[3];
		if (!((TokenIdentifier) constructor.children[0].reduceToToken()).getValue().equals(id))
			throw new Exception("Constructor has mismatching id with structure, expected id: " + id);

		
		List<SyntaxNode> varDeclKnots;
		if (constructor.children.length == 7) {
			cArgs = new String[] {};
			cArgTypes = new Type[] {};
			varDeclKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) constructor.children[4]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) constructor.children[5]).root;
		} else {
			List<String> cArgs = new LinkedList<>();
			List<Type> cArgTypes = new LinkedList<>();
			SyntaxKnot cArgKnot = (SyntaxKnot) constructor.children[2];
			while (cArgKnot.children.length != 2) {
				cArgTypes.add(Type.inferType((SyntaxExpressionKnot) cArgKnot.children[0]));
				cArgs.add(((TokenIdentifier) cArgKnot.children[1].reduceToToken()).value);
				cArgKnot = (SyntaxKnot) cArgKnot.children[3];
			}
			cArgs.add(((TokenIdentifier) cArgKnot.children[1].reduceToToken()).value);
			cArgTypes.add(Type.inferType((SyntaxExpressionKnot) cArgKnot.children[0]));
			this.cArgs = new String[cArgs.size()];
			for(int i=0; i<this.cArgs.length; i++)
				this.cArgs[i] = cArgs.get(i);
			this.cArgTypes = new Type[cArgTypes.size()];
			for(int i=0; i<this.cArgTypes.length; i++)
				this.cArgTypes[i] = cArgTypes.get(i);
			varDeclKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) constructor.children[5]);
			body = TreeProcessing.processIntoAST((SyntaxKnot) constructor.children[6]).root;
		}
		varDecls = new VarDeclNode[varDeclKnots.size()];
		counter = 0;
		for (SyntaxNode varDeclKnot : varDecls) {
			varDecls[counter++] = new VarDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		branchAddress = domain.addStructDeclaration(id, attributes, functions, cArgTypes);
		IDDeclarationBlock tempDomain = new IDDeclarationBlock(domain, Scope.STRUCT);
		for (VarDeclNode varDecl : attributes) {
			varDecl.checkTypes(tempDomain, Scope.STRUCT);
		}
		for (FunDeclNode funDecl : functions) {
			funDecl.checkTypes(tempDomain, Scope.STRUCT);
		}
		
		//TypeChecking the constructor
		for (VarDeclNode varDecl : varDecls) {
			varDecl.checkTypes(tempDomain, Scope.LOCAL);
		}
		body.checkTypes(tempDomain, Scope.LOCAL);
		return;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.SyntaxNode#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Label to skip the struct declaration if the code comes here some other way
		stack.add("bra " + branchAddress + "Skip");
		//load the global environment address
		stack.add(branchAddress + ": ldl 1");
		stack.add("str 6");
		stack.add("link " + (cArgs.length + varDecls.length+2));
		
		//store the global environment address in the new local memory
		stack.add("ldr 6");
		stack.add("stl 1");
		stack.add("ldr 5");
		stack.add("stml 3 " + cArgs.length);
		for(int i=0; i<attributes.length;i++)
			stack.add("ldc 0");
		stack.add("stmh "+attributes.length);
		stack.add("stl 2");
		for(VarDeclNode attribute : attributes) {
			attribute.addCodeToStack(stack, counter);
		}
		for(VarDeclNode varDecl : varDecls) {
			varDecl.addCodeToStack(stack, counter);
		}
		// Generate the code of the constructor body
		body.addCodeToStack(stack, counter);
		//Put the address of the struct on top of the stack.
		stack.add("ldl 2");
		stack.add("str 4");
		stack.add("unlink");
		stack.add("ldr 4");
		stack.add("swp");
		//Return back to where we were.
		stack.add("ret");
		
		// Generate the code for all the functions in the struct
		for(FunDeclNode funDecl : functions) {
			funDecl.addCodeToStack(stack, counter);
		}
		// Label for the skip to go to
		stack.add(branchAddress + "Skip: nop");
	}



}
