/**
 * 
 */
package tree.ast.expressions.structs;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.NoArg;
import tree.ast.types.Type;
import tree.ast.types.specials.WildType;

/**
 * @author Flip van Spaendonck
 *
 */
public class NullExpr extends  NoArg {

	public final static NullExpr instanceOf = new NullExpr();
	
	private NullExpr() {}
	
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldc 0");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		return WildType.instanceOf;
	}

}
