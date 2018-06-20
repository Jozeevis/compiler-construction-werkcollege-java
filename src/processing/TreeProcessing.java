/**
 * 
 */
package processing;

import java.util.LinkedList;
import java.util.List;

import grammar.ExpressionWithAST;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import lexer.TokenTupleFunction;
import lexer.TokenType;
import tree.ast.IfElseStmtKnot;
import tree.ast.PrintNode;
import tree.ast.ReturnNode;
import tree.ast.StructDeclNode;
import tree.ast.VarDeclNode;
import tree.ast.WhileStmtKnot;
import tree.ast.accessors.Accessor;
import tree.ast.accessors.ListHeadAccessor;
import tree.ast.accessors.ListTailAccessor;
import tree.ast.accessors.MupleAccessor;
import tree.ast.accessors.StructFuncAccessor;
import tree.ast.accessors.StructVarAccessor;
import tree.ast.expressions.FunCall;
import tree.ast.expressions.IllegalThisException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxNode;
import tree.SyntaxTree;
import tree.IDDeclarationBlock.Scope;
import tree.ast.ASyntaxKnot;
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;

/**
 * A class containing utility functions for processing syntax trees.
 * 
 * @author Flip van Spaendonck
 */
public final class TreeProcessing {

	public static SyntaxTree processIntoAST(SyntaxKnot originalKnot) throws Exception {
		return processIntoAST(originalKnot, null);
	}
	/**
	 * Tries to detect knots with an expression that has an abstract syntax tree
	 * equivallent and converts does knots into ASyntaxKnots.
	 * 
	 * @param tree
	 *            the tree that is used to create a converted copy. This tree is not
	 *            altered in any way.
	 * @return a new tree with all possible knots converted into ASyntaxKnots,
	 *         inconvertible knots are still present and are represented by a
	 *         SyntaxKnot.
	 * @throws Exception
	 */
	public static SyntaxTree processIntoAST(SyntaxKnot originalKnot, SyntaxKnot fatheredInKnot) throws Exception {
		SyntaxTree asTree = new SyntaxTree((SyntaxKnot) originalKnot.getASTEquivalent(fatheredInKnot));
		return asTree;
	}

	public static boolean checkWellTyped(SyntaxTree tree) {
		try {
			IDDeclarationBlock domain = new IDDeclarationBlock();
			tree.root.checkTypes(domain, Scope.GLOBAL);
			tree.nrOfGlobals = domain.globalVars.size();
			tree.mainAddress = domain.findFunDeclaration("main").branchAddress;
		} catch (TypeException e) {
			e.printStackTrace();
			return false;
		} catch (DeclarationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Extracts all SyntaxNodes from a starNode structure.
	 */
	public static List<SyntaxNode> extractFromStarNode(SyntaxKnot starNode) {
		List<SyntaxNode> nodes = new LinkedList<>();
		SyntaxKnot currentKnot = starNode;
		while (currentKnot.children.length == 2) {
			nodes.add(currentKnot.children[0]);
			currentKnot = (SyntaxKnot) currentKnot.children[1];
		}
		return nodes;
	}

	public static List<SyntaxNode> extractFromPlusNode(SyntaxKnot plusNode) {
		List<SyntaxNode> nodes = new LinkedList<>();
		SyntaxKnot currentKnot = plusNode;
		while (currentKnot.children.length == 2) {
			nodes.add(currentKnot.children[0]);
			currentKnot = (SyntaxKnot) currentKnot.children[1];
		}
		nodes.add(currentKnot);
		return nodes;
	}

	public static Accessor[] processFieldStar(SyntaxExpressionKnot fieldStar) throws IllegalThisException {
		List<SyntaxNode> fieldNodes = TreeProcessing.extractFromStarNode(fieldStar);		
		Accessor[] accessors = new Accessor[fieldNodes.size()];
		int counter = 0;
		for (SyntaxNode fieldNode : fieldNodes) {
			if (((SyntaxKnot) fieldNode).children[1] instanceof SyntaxLeaf) {
				SyntaxLeaf leaf = (SyntaxLeaf) ((SyntaxKnot) fieldNode).children[1];
				if (leaf.leaf.getTokenType() == TokenType.TOK_LISTFUNC_HEAD) {
					accessors[counter] = ListHeadAccessor.instance;
				} else if (leaf.leaf.getTokenType() == TokenType.TOK_LISTFUNC_TAIL) {
					accessors[counter] = ListTailAccessor.instance;
				} else if (leaf.leaf instanceof TokenIdentifier) {
					accessors[counter] = new StructVarAccessor(((TokenIdentifier)leaf.leaf).value);
				} else if (leaf.leaf.getTokenType() == TokenType.TOK_LIST_OPEN) {
					int index = ((TokenInteger)((SyntaxKnot) fieldNode).children[2].reduceToToken()).value;
					accessors[counter] = new MupleAccessor(index);
				}
			} else {
				accessors[counter] = new StructFuncAccessor(new FunCall((SyntaxExpressionKnot) ((SyntaxKnot) fieldNode).children[1]));
			}
			//TODO: handle funcalls
			counter++;
		}
		return accessors;
	}

}
