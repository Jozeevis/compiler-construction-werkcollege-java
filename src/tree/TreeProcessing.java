/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import grammar.ExpressionWithAST;
import tree.ast.IfElseStmtKnot;
import tree.ast.ReturnNode;
import tree.ast.VarDeclNode;
import tree.ast.WhileStmtKnot;
import tree.ast.ASyntaxKnot;
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;
import tree.ast.ICodeBlock;
import tree.ast.IDDeclarationBlock;
import tree.ast.ITypeCheckable;

/**
 * A class containing utility functions for processing syntax trees.
 * @author Flip van Spaendonck
 */
public final class TreeProcessing {

	/**
	 * Tries to detect knots with an expression that has an abstract syntax tree equivallent and converts does knots into ASyntaxKnots.
	 * @param tree the tree that is used to create a converted copy. This tree is not altered in any way.
	 * @return a new tree with all possible knots converted into ASyntaxKnots, inconvertible knots are still present and are represented by a SyntaxKnot.
	 * @throws Exception 
	 */
	public static SyntaxTree processIntoAST(SyntaxKnot originalKnot) throws Exception {
		List<SyntaxExpressionKnot> oldTreefrontier = new LinkedList<>();
		SyntaxTree asTree = new SyntaxTree(
				new SyntaxExpressionKnot(((SyntaxExpressionKnot)originalKnot).expression, null)
				);
		for (SyntaxNode node : asTree.root.children) {
			if (node instanceof SyntaxExpressionKnot) {
				oldTreefrontier.add((SyntaxExpressionKnot)node);
			}
		}
		while(!oldTreefrontier.isEmpty()) {
			SyntaxExpressionKnot current = oldTreefrontier.remove(0);
			
			if (current.expression instanceof ExpressionWithAST) {
				ASyntaxKnot aKnot;
				switch (((ExpressionWithAST) current.expression).id) {
					case "VarInit":
						aKnot = new VarDeclNode(current, asTree.frontier);
						break;
					case "FunDecl":
						aKnot = new FunDeclNode(current, asTree.frontier);
						break;
					case "IfElseStmt":
						aKnot = new IfElseStmtKnot(current, asTree.frontier);
						break;
					case "WhileStmt":
						aKnot = new WhileStmtKnot(current, asTree.frontier);
						break;
					case "FunCall":
						aKnot = new FunCallNode(current, asTree.frontier);
						break;
					case "Assign":
						aKnot = new AssignmentNode(current, asTree.frontier);
						break;
					case "Return":
						aKnot = new ReturnNode(current, asTree.frontier);
						break;
					default:
						throw(new Exception("ExpressionWithAST encountered, however no option has been programmed in."));
					//TODO: Add other Expressions that can be converted to AST Nodes.
				}
				asTree.frontier.addChild(aKnot);
			} else {
				asTree.frontier.addChild(new SyntaxExpressionKnot(current.expression, asTree.frontier));
				for (SyntaxNode node : current.children) {
					if (node instanceof SyntaxExpressionKnot) {
						oldTreefrontier.add(0, (SyntaxExpressionKnot)node);
					}
				}
			}
			while(asTree.frontier != null && asTree.frontier.isComplete()) {
				asTree.frontier = asTree.frontier.parent;
			}
		}
		return asTree;
	}
	
	public static boolean checkWellTyped(SyntaxTree tree) {
		List<SyntaxNode> frontier = new LinkedList<>();
		
		IDDeclarationBlock block = null;
		frontier.add(tree.root);
		while(!frontier.isEmpty()) {
			SyntaxNode current = frontier.remove(0);
			if (current instanceof ITypeCheckable) {
				if (!((ITypeCheckable)current).checkTypes(block))
					return false;
			}
			if (current instanceof ICodeBlock) {
				block = ((ICodeBlock) current).getBlock(block);
			}
			if (current instanceof SyntaxKnot) {
				for (SyntaxNode node  :((SyntaxKnot) current).getChildren()) {
					frontier.add(node);
				}
			}
		}
		return true;
	}
	
}
