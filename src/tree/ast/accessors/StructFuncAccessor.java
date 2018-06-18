/**
 * 
 */
package tree.ast.accessors;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.FunDeclaration;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.expressions.FunCall;
import tree.ast.types.CustomType;
import tree.ast.types.StructType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class StructFuncAccessor extends Accessor {

	public final FunCall funcall;
	
	public StructFuncAccessor(FunCall funcall) {
		this.funcall = funcall;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain, Type suppliedType) throws TypeException, DeclarationException {
		if (suppliedType instanceof CustomType) {
			suppliedType = domain.findStructDeclaration(((CustomType)suppliedType).typeName).structType;
		}
		if (suppliedType instanceof StructType) {
			FunDeclaration attributeDeclaration = ((StructType)suppliedType).findFunDeclaration(funcall.id);
			return funcall.checkTypes(attributeDeclaration, domain);
		}
		throw new TypeException(
				"Left supplied type was of type: " + suppliedType + ", while a StructType was expected.");
	}
	
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		funcall.addCodeToStack(stack, counter);
	}

}
