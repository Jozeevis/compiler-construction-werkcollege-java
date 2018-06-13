package tree;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.ASyntaxKnot;
import tree.ast.IDDeclarationBlock;
import tree.ast.ITypeCheckable;
import tree.ast.LabelCounter;
import tree.ast.expressions.BaseExpr;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class PrintNode extends ASyntaxKnot implements ITypeCheckable{

	public final BaseExpr expression;
	
	private Type expressionType;
	
	public PrintNode(SyntaxKnot oldKnot, SyntaxKnot parent) {
		super(parent);
		expression = BaseExpr.convertToExpr((SyntaxExpressionKnot) oldKnot.children[1]);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Put code for the expression evaluating to the argument on the stack
		expression.addCodeToStack(stack, counter);
		// If the argument is of type char, print the result as a unicode character
		if (expressionType.matches(BaseType.instanceChar)) {
			stack.add("trap 1");
		}
		else {
			stack.add("trap 0");
		}
	}

	@Override
	public IDDeclarationBlock checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		expressionType = expression.checkTypes(domain);
		return domain;
	}
}

