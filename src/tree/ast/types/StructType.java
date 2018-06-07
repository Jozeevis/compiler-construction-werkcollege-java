/**
 * 
 */
package tree.ast.types;


import tree.ast.IDDeclarationBlock;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.structs.NullExpr;
import tree.ast.types.specials.WildType;

/**
 * The Type used to fully describe the declaration of a custom type, these
 * should only be made in a StructDeclNode.
 * 
 * @author Flip van Spaendonck
 *
 */
public class StructType extends Type {

	public final IDDeclarationBlock declarations;
	public final String structName;
	
	public StructType(String structName, IDDeclarationBlock declBlock) {
		this.structName = structName;
		declarations = declBlock;
	}
	
	/**
	 * It should be noted that checking type equality with a StructType and another type should not be done.
	 */
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.ast.types.Type#getNullValue()
	 */
	@Override
	public BaseExpr getNullValue() {
		return NullExpr.instanceOf;
	}

	/**
	 * Returns the matching constructor branch name with the given type signature, if no matching constructor was found this returns null.
	 * @param argumentTypes
	 * @return
	 */
	public String getMatchingConstructor(Type[] argumentTypes) {
		return null;
	}

}
