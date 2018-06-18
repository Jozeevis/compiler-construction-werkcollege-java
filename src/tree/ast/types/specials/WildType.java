/**
 * 
 */
package tree.ast.types.specials;

import tree.ast.expressions.BaseExpr;
import tree.ast.types.Type;

/**
 * A wildcard type that is equal to every other type.
 * @author Flip van Spaendonck
 *
 */
public class WildType extends Type {
	
	public final static WildType instanceOf = new WildType();

	@Override
	public boolean matches(Type t) {
		return true;
	}

	@Override
	public BaseExpr getNullValue() {
		return null;
	}
}
