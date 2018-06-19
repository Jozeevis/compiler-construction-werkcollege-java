package tree.ast.expressions.num;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class NumConstant extends NoArg{
    public final int constant;

    public NumConstant(int val) {
        this.constant = val;
    }

    @Override
    public String toString() {
        return ""+ constant;
    }

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc "+constant);
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return BaseType.instanceInt;
	}
    
    
}
