package tree.ast.types;

/**
 * A Type object used to represent the type of a function.
 * @author Lars Kuijpers
 */
public class FunctionType extends Type {

	public final Type[] inputTypes;	// Types on the left side of the arrow (can be empty)
	public final Type returnType;	// Type on the right side of the arrow (can be null)

	public FunctionType(Type[] left, Type returnType) {
		inputTypes = left;
		this.returnType = returnType;
	}
}