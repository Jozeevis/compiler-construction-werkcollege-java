/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import grammar.Expression;
import grammar.ExpressionWithAST;
import tree.ast.VarDeclKnot;

/**
 * A class containing utility functions for processing syntax trees.
 * @author Flip van Spaendonck
 */
public final class TreeProcessing {

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
						asTree.frontier.add(new VarDeclKnot(current, asTree.frontier));
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
	
}
