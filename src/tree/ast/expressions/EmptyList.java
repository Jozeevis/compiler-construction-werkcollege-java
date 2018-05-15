/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;

/**
 * @author Flip van Spaendonck
 *
 */
public class EmptyList extends NoArg {

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return true;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc 0");
	}

}
