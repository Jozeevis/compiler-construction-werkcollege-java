/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import grammar.Expression;
import grammar.ExpressionWithAST;
import tree.ast.ASyntaxKnot;
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;
import tree.ast.IfElseStmtKnot;
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

	public static List<String> generateCode(SyntaxTree tree) {
		/** Contains the full code that will be generated */
		List<String> code = new LinkedList<String>();
		/** 
		 * Contains all the codelines for the functions that will be defined
		 * These will be placed at the end of the full code
		 */
		List<String> functionCodes = new LinkedList<String>();
		/**  */
		List<SyntaxKnot> frontier = new LinkedList<SyntaxKnot>();
		SyntaxKnot current = tree.root;

		Integer labelCounter = 0;

		if (((SyntaxExpressionKnot)current).expression instanceof Expression) {
			// Generate code for the expression
			generateCode(BaseExpr.convertToExpr((SyntaxExpressionKnot)current));
		}
		else if (current instanceof VarDeclNode) {
			// Generate code for the body of the declaration
			generateCode(((VarDeclNode) current).body.expression);
			// TODO: Code to actually declare the variable itself
		}
		else if (current instanceof FunDeclNode) {
			// TODO: Make this work with overloaded functions
			// Label to jump to the function body
			code.add(((FunDeclNode)current).id + ": nop");
			// TODO: Figure out how to actually use variables here?
			// FIXME: rewrite this code so this is possible
			generateCode(((FunDeclNode)current).body);
			// TODO: Need to save original enter point somewhere so return can make it back there
		}
		else if (current instanceof IfElseStmtKnot) {
			// Generate the check expression body
			generateCode(((IfElseStmtKnot)current).check.expression);
			// Number that will be used for all labels in this statement
			Integer label = labelCounter;
			labelCounter++;
			code.add("brt ELSELABEL" + label);
			// FIXME: rewrite this code so this is possible
			generateCode(((IfElseStmtKnot)current).ifBody);
			// Label used when the condition is false to skip the if-body
			code.add("bra ENDLABEL" + label);
			code.add("ELSELABEL" + label + ": nop");
			// FIXME: rewrite this code so this is possible
			generateCode(((IfElseStmtKnot)current).elseBody);
			// Label used when the condition is true to skip the else-body
			code.add("ENDLABEL" + label + ": nop");
		}
		else if (current instanceof WhileStmtKnot) {
			// Number that will be used for all labels in this statement
			Integer label = labelCounter;
			labelCounter++;
			// Label for checking the condition loop
			code.add("CHECKLABEL" + label);
			// Generate the check expression body
			generateCode(((WhileStmtKnot) current).check.expression);
			// If the condition is false, jump out of the loop
			code.add("brf ENDLABEL" + label);
			// FIXME: rewrite this code so this is possible
			generateCode(((WhileStmtKnot) current).body);
			// Jump back to the while check
			code.add("bra CHECKLABEL" + label);
			// Label for jumping out of the loop
			code.add("ENDLABEL" + label + ": nop");
		
		}
		else if (current instanceof FunCallNode) {
			generateCode(((FunCallNode) current).funCall);
		}
		else if (current instanceof AssignmentNode) {
			if (((AssignmentNode)current).accessors.length == 0) {
				// Generate code for the assignment body
				generateCode(((AssignmentNode)current).expression.expression);
				// Save the result in the heap address given by the linknumber
				// TODO: Make this work for lists and tuples
				code.add("stl " +((AssignmentNode)current).getLinkNumber());
			}
			else {
				// TODO: Implement accessors
			}
		}
		else if (current instanceof ReturnNode) {
			// TODO: See above, need to save the returnpoint somewhere and the jump to it here
			code.add("ret");
		}
		code.addAll(functionCodes);
		return code;
	}
	
	public static List<String> generateCode(BaseExpr expression) {
		List<String> out = new LinkedList<>();
		List<BaseExpr> frontier = new LinkedList<>();
		frontier.add(expression);
		while(!frontier.isEmpty()) {
			BaseExpr current = frontier.remove(0);
			if (current instanceof Variable) {
				out.add("ldl "+((Variable) current).getLinkNumber());
				out.add("ldh 0");
			} else if (current instanceof TupleExp) {
				out.add("stmh 2");
				out.addAll(generateCode(((TupleExp) current).getRight()));
				out.addAll(generateCode(((TupleExp) current).getLeft()));
			} else {
				//Simple operators
				out.add(current.getCode());
				if (current instanceof TwoArg) {
					frontier.add(((TwoArg) current).getRight());
					frontier.add(((TwoArg) current).getLeft());
				} else if (current instanceof OneArg) {
					frontier.add(((OneArg) current).getValue());
				} else {
					//Check what kind of Expr this is, twoArg, oneArg, noArg and add the code to the list.
				}
			}
		}
		return out;
	}
}
