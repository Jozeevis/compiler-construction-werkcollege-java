package tree.ast.expressions;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.bool.BoolConstant;
import tree.ast.expressions.list.EmptyList;
import tree.ast.types.BaseType;
import tree.ast.types.ListType;
import tree.ast.types.Type;

public class IsEmpty extends OneArg {

    public IsEmpty(BaseExpr exp) {
        super(exp);
    }

	@Override
	public BaseExpr optimize() {
        val = val.optimize();
        if (val instanceof EmptyList) {
            return new BoolConstant(true);
        }
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Put code for the expression evaluating to the argument on the stack
        val.addCodeToStack(stack, counter);

        // Put zero on top of the stack
        stack.add("ldc 0");
        // Check if the evaluated argument is equal to zero and add the result to the
        // stack (1 for true, 0 for false)
        stack.add("eq");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type expressionType;
		if (!((expressionType = val.checkTypes(domain)) instanceof ListType))
			throw new TypeException("Expression was of type: "+expressionType+" while type List was expected.");
		return BaseType.instanceBool;
	}

}