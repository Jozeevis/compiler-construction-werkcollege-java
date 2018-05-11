/**
 * 
 */
package tree.ast.expressions.bool;

import tree.ast.IDDeclarationBlock;
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
	public String getCode() {
		// TODO: why not use 1 here? - Lars
		return "ldc "+ (constant? 0xFFFFFFFF:0);
	}
}
