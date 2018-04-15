/**
 * 
 */
package tree.ast.expressions;

import java.util.List;

import lexer.TokenIdentifier;
import tree.IDDeclaration;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.ast.types.FunctionType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class FunCall extends BaseExpr {
	
	public final Type expectedType;
	public final String id;
	
	public final BaseExpr[] arguments;
	public final Type[] argumentTypes;

	public FunCall(SyntaxKnot funcall, Type expectedType) {
		this.expectedType = expectedType;
		id = ((TokenIdentifier)((SyntaxLeaf)funcall.children[0]).leaf).value;
		
		if (funcall.children.length == 3) {
			arguments = new BaseExpr[0];
			argumentTypes = new Type[0];
		} else {
			int n=0;
			SyntaxKnot currentArgument = (SyntaxKnot) funcall.children[3];
			while(currentArgument.children.length == 3) {
				currentArgument = (SyntaxKnot) currentArgument.children[2];
				n++;
			}
			arguments = new BaseExpr[n];
			argumentTypes = new Type[n];
			currentArgument = (SyntaxKnot) funcall.children[3];
			int i = 0;
			while(true) {
				arguments[i] = BaseExpr.convertToExpr((SyntaxKnot) currentArgument.children[0]);
				argumentTypes[i] = Type.inferExpressionType((SyntaxKnot) currentArgument.children[0]);
				if (currentArgument.children.length == 3) {
					currentArgument = (SyntaxKnot) currentArgument.children[2];
					i++;
				} else {
					break;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		for(BaseExpr argument : arguments)
			argument.optimize();
		return this;
	}
	
	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		FunctionType type = null;
		for(IDDeclaration declaration : domain) {
			if (declaration.id.equals(id)) {
				if (!(declaration.type instanceof FunctionType))
					return false;
				type = (FunctionType) declaration.type;
				break;
			}
		}
		if (type == null)
			return false;
		if (!type.returnType.equals(expectedType))
			return false;
		if (type.inputTypes.length != arguments.length)
			return false;
		for(int i=0; i<arguments.length; i++) {
			if (!argumentTypes[i].equals(type.inputTypes[i]))
				return false;
			if (!arguments[i].checkTypes(domain))
				return false;
		}
		return false;
	}

}
