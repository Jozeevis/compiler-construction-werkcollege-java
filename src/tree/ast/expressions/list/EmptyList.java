/**
 * 
 */
package tree.ast.expressions.list;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.ListType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class EmptyList extends NoArg {

	public final Type expectedType;
	
	public EmptyList(Type expectedType) {
		this.expectedType = expectedType;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc 0");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return new ListType(expectedType);
	}

}
