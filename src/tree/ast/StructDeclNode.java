/**
 * 
 */
package tree.ast;

import java.util.List;

import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructDeclNode extends ASyntaxKnot implements ITypeCheckable {

	public final VarDeclNode[] variables;
	
	public final FunDeclNode[] functions;
	
	public StructDeclNode(SyntaxKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);
		List<SyntaxNode> varKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[2]);
		variables = new VarDeclNode[varKnots.size()];
		int counter = 0;
		for(SyntaxNode varDeclKnot : varKnots) {
			variables[counter++] = new VarDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
		List<SyntaxNode> funcKnots = TreeProcessing.extractFromStarNode((SyntaxKnot) oldKnot.children[4]);
		functions = new FunDeclNode[funcKnots.size()];
		counter = 0;
		for(SyntaxNode varDeclKnot : varKnots) {
			functions[counter++] = new FunDeclNode((SyntaxExpressionKnot) varDeclKnot, frontier);
		}
		
		
	}

	@Override
	protected SyntaxNode[] initializeChildrenArray() {
		return new SyntaxNode[] {};
	}

	/* (non-Javadoc)
	 * @see tree.SyntaxNode#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// TODO Auto-generated method stub
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
