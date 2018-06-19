package tree;

import java.util.List;

import grammar.Expression;
import grammar.ExpressionWithAST;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock.Scope;
import tree.ast.ASyntaxKnot;
import tree.ast.AssignmentNode;
import tree.ast.FunCallNode;
import tree.ast.FunDeclNode;
import tree.ast.IfElseStmtKnot;
import tree.ast.LabelCounter;
import tree.ast.ReturnNode;
import tree.ast.StructDeclNode;
import tree.ast.VarDeclNode;
import tree.ast.WhileStmtKnot;

/**
 * A knot in the syntax-tree data structure.
 * A syntax expression knot does not hold an ALU expression but a grammatical expression from the Grammar package.
 * @author Flip van Spaendonck
 */
public class SyntaxExpressionKnot extends SyntaxKnot{

	/** The expression that this node represents**/
	public Expression expression;
	
	public SyntaxExpressionKnot(Expression expression, SyntaxKnot frontier) {
		super(frontier);
		this.expression = expression;
		children = new SyntaxNode[expression.expression.length];
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		System.out.println(expression);
		for(SyntaxNode child : children) {
			child.addCodeToStack(stack, counter);
		}
	}
	
	@Override
	public String toString() {
		return ""+expression;
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		System.out.println(this);
		for(SyntaxNode child : children)
			child.checkTypes(domain, scope);
	}

	@Override
	public SyntaxNode getASTEquivalent(SyntaxKnot parent) throws Exception {
		if (expression instanceof ExpressionWithAST) {
			ASyntaxKnot aKnot;
			switch (((ExpressionWithAST) expression).id) {
			case "VarInit":
				aKnot = new VarDeclNode(this, parent);
				break;
			case "FunDecl":
				aKnot = new FunDeclNode(this, parent);
				break;
			case "StructDecl":
				aKnot = new StructDeclNode(this, parent);
				break;
			case "IfElseStmt":
				aKnot = new IfElseStmtKnot(this, parent);
				break;
			case "WhileStmt":
				aKnot = new WhileStmtKnot(this, parent);
				break;
			case "FunCall":
				aKnot = new FunCallNode(this, parent);
				break;
			case "Assign":
				aKnot = new AssignmentNode(this, parent);
				break;
			case "Return":
				aKnot = new ReturnNode(this, parent);
				break;
			case "Print":
				aKnot = new PrintNode(this, parent);
				break;
			default:
				throw (new Exception("ExpressionWithAST: " + (((ExpressionWithAST) expression).id)
						+ " encountered, however no option has been programmed in."));
				// TODO: Add other Expressions that can be converted to AST Nodes.
			}
			return aKnot;
		} else {
			SyntaxExpressionKnot clone = new SyntaxExpressionKnot(expression, parent);
			for(SyntaxNode child : children) {
				clone.addChild(child.getASTEquivalent(clone));
			}
			return clone;
		}
	}

}
