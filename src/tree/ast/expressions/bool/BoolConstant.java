/**
 * 
 */
package tree.ast.expressions.bool;

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
}
