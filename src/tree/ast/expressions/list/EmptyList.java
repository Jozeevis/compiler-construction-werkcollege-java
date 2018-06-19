/**
 * 
 */
package tree.ast.expressions.list;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.ListType;
import tree.ast.types.Type;
import tree.ast.types.specials.WildType;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class EmptyList extends NoArg {

	
	public static final EmptyList instanceOf = new EmptyList();

	private EmptyList() {}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc 0");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return new ListType(WildType.instanceOf);
	}

}
