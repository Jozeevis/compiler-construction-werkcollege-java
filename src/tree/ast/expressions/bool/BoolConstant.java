/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;

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
	public boolean checkTypes(IDDeclarationBlock domain) {
		return true;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc "+ (constant? 0xFFFFFFFF:0));
	}
}
