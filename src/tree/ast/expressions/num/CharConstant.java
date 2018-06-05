/**
 * 
 */
package tree.ast.expressions.num;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.BaseType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class CharConstant extends NoArg {

	public final char value;
	
	public CharConstant(char value) {
		this.value = value;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc "+String.format("%04x", (int) value));
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return BaseType.instanceChar;
	}

}
