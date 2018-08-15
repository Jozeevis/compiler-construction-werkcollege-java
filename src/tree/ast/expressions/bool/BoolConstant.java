/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class BoolConstant extends NoArg {

	public final boolean constant;
	
	public BoolConstant(boolean constant) {
		this.constant = constant;
	}


	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc "+ (constant? 0xFFFFFFFF:0)+"\n");
	}


	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return BaseType.instanceBool;
	}
}
