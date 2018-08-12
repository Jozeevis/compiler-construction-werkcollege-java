package tree.ast;

import java.util.List;

import lexer.TokenExpression;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxKnot;
import tree.IDDeclarationBlock.Scope;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class PrintNode extends ASyntaxKnot {

	public final BaseExpr expression;
	
	private Type expressionType;
	
	public PrintNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		expression = ((TokenExpression) oldKnot.children[1].reduceToToken()).expression;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Put code for the expression evaluating to the argument on the stack
		expression.addCodeToStack(stack, counter);
		// If the argument is of type char, print the result as a unicode character
		if (expressionType.matches(BaseType.instanceChar)) {
			stack.add("trap 1\n");
		}
		else {
			stack.add("trap 0\n");
		}
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		expressionType = expression.checkTypes(domain);
	}

	@Override
	public boolean alwaysReturns() {
		return false;
	}
}

