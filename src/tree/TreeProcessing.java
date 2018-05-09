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
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;
import tree.ast.IDeclarable;
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
	 */
	public static SyntaxTree processIntoAST(SyntaxTree tree) {
		List<SyntaxKnot> frontier = new LinkedList<>();
		SyntaxTree asTree = new SyntaxTree(new SyntaxKnot(tree.root.expression, null));
		for (SyntaxNode node : asTree.root.children) {
			if (node instanceof SyntaxKnot) {
				frontier.add((SyntaxKnot)node);
			}
		}
		while(!frontier.isEmpty()) {
			SyntaxKnot current = frontier.remove(0);
			if (current.expression instanceof ExpressionWithAST) {
				switch (((ExpressionWithAST) current.expression).id) {
					case "VarInit":
						asTree.frontier.add(new VarDeclNode(current, asTree.frontier));
						break;
					case "FunDecl":
						asTree.frontier.add(new FunDeclNode(current, asTree.frontier));
						break;
					case "IfElseStmt":
						asTree.frontier.add(new IfElseStmtKnot(current, asTree.frontier));
						break;
					case "WhileStmt":
						asTree.frontier.add(new WhileStmtKnot(current, asTree.frontier));
						break;
					case "FunCall":
						asTree.frontier.add(new FunCallNode(current, asTree.frontier));
						break;
					case "Assign":
						asTree.frontier.add(new AssignmentNode(current, asTree.frontier));
						break;
					case "Return":
						asTree.frontier.add(new ReturnNode(current, asTree.frontier));
						break;
					//TODO: Add other Expressions that can be converted to AST Nodes.
				}
			} else {
				asTree.frontier.add(new SyntaxKnot(current.expression,asTree.frontier));
				for (SyntaxNode node : current.children) {
					if (node instanceof SyntaxKnot) {
						frontier.add((SyntaxKnot)node);
					}
				}
			}
			while(tree.frontier != null && tree.frontier.isComplete()) {
				tree.frontier = tree.frontier.parent;
			}
		}
		return tree;
	}
	
	public static boolean checkWellTyped(SyntaxTree tree) {
		List<SyntaxNode> frontier = new LinkedList<>();
		List<IDDeclaration> declarations = new LinkedList<>();
		frontier.add(tree.root);
		while(!frontier.isEmpty()) {
			SyntaxNode current = frontier.remove(0);
			if (current instanceof ITypeCheckable) {
				if (!((ITypeCheckable)current).checkTypes(declarations))
					return false;
			}
			if (current instanceof IDeclarable) {
				declarations.add(((IDeclarable)current).getDeclaration());
			}
			if (current instanceof IKnot) {
				for (SyntaxNode node  :((IKnot) current).getChildren()) {
					frontier.add(node);
				}
			}
		}
		return true;
	}
	
}
