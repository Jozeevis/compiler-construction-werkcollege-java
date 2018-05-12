/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import tree.ast.IDDeclarationBlock;

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
	public void addCodeToStack(List<String> stack) {
		stack.add("ldc 0");
	}

}
