/**
 * 
 */
package tree.ast.expressions.bool;

import java.util.List;

import tree.IDDeclaration;
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
	public boolean checkTypes(List<IDDeclaration> domain) {
		return true;
	}

	@Override
	public String getCode() {
		return "ldc "+ (constant? 1:0);
	}
}
