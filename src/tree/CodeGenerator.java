/**
 * 
 */
package tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import grammar.Expression;
import grammar.ExpressionWithAST;
import tree.ast.ASyntaxKnot;
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;
import tree.ast.IfElseStmtKnot;
import tree.ast.LabelCounter;
import tree.ast.VarDeclNode;
import tree.ast.WhileStmtKnot;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;
import tree.ast.expressions.TupleExp;
import tree.ast.expressions.TwoArg;
import tree.ast.expressions.Variable;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class CodeGenerator {
	
	public static List<String> generateCode(SyntaxNode node) {
		/** Contains the full code that will be generated. */
		List<String> stack = new LinkedList<String>();
		/** A counter used to keep track of what label we're at. */
		LabelCounter counter = new LabelCounter();
		node.addCodeToStack(stack, counter);
		return stack;
	}
}
