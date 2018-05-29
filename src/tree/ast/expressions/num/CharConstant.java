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

	public CharConstant(char value) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#addCodeToStack(java.util.List, tree.ast.LabelCounter)
	 */
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return BaseType.instanceChar;
	}

}
