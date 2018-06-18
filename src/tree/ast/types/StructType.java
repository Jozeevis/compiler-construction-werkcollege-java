/**
 * 
 */
package tree.ast.types;


import java.util.List;

import processing.DeclarationException;
import tree.FunDeclaration;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.structs.NullExpr;
import tree.ast.types.specials.WildType;

/**
 * The Type used to fully describe the declaration of a custom type, these
 * should only be made in a StructDeclNode.
 * 
 * @author Flip van Spaendonck
 *
 */
public class StructType extends Type {

	public final String structName;
	public final List<IDDeclaration> structVars;
	public final List<FunDeclaration> structFuncs;
	
	

	
	public StructType(String id, List<IDDeclaration> structVars, List<FunDeclaration> structFuncs) {
		structName = id;
		this.structVars =structVars;
		this.structFuncs = structFuncs;
	}

	/**
	 * It should be noted that checking type equality with a StructType and another type should not be done.
	 */
	@Override
	public boolean matches(Type t) {
		if (t instanceof WildType)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tree.ast.types.Type#getNullValue()
	 */
	@Override
	public BaseExpr getNullValue() {
		return NullExpr.instanceOf;
	}

	public FunDeclaration findFunDeclaration(String id) throws DeclarationException {
		for(FunDeclaration structFun : structFuncs) {
			if(structFun.id.equals(id))
				return structFun;
		}
		throw new DeclarationException("No function with id: " + id + " has been declared.");
	}

	public IDDeclaration findIDDeclaration(String id) throws DeclarationException {
		for(IDDeclaration structVar : structVars) {
			if(structVar.id.equals(id))
				return structVar;
		}
		throw new DeclarationException("No function with id: " + id + " has been declared.");
	}

}
