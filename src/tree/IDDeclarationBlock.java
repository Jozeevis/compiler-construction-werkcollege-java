/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import processing.DeclarationException;
import tree.ast.FunDeclNode;
import tree.ast.VarDeclNode;
import tree.ast.types.StructType;
import tree.ast.types.Type;
import tree.ast.types.specials.FunctionType;

/**
 * A data structure used to keep track of what should be in the local memory.
 * TODO: the block should throw an error whenever an id is added that is already declared in that scope.
 * @author Flip van Spaendonck
 */
public class IDDeclarationBlock {
	
	public enum Scope {GLOBAL, STRUCT, LOCAL; };

	public final List<IDDeclaration> globalVars;
	public final List<FunDeclaration> globalFuncs;
	public final List<StructDeclaration> globalStructs;
	
	public StructType currentStruct;
	
	public final List<IDDeclaration> localVars;
	
	
	public IDDeclarationBlock() {
		globalVars = new LinkedList<>();
		globalFuncs = new LinkedList<>();
		globalStructs = new LinkedList<>();
		localVars = new LinkedList<>();
	}
	
	public IDDeclarationBlock(IDDeclarationBlock original, Scope scope) {
		switch(scope) {
		case GLOBAL:
			globalVars = new LinkedList<>(original.globalVars);
			globalFuncs = new LinkedList<>(original.globalFuncs);
			globalStructs = new LinkedList<>(original.globalStructs);
			localVars = new LinkedList<>();
			break;
		case STRUCT:
			globalVars = new LinkedList<>(original.globalVars);
			globalFuncs = new LinkedList<>(original.globalFuncs);
			globalStructs = new LinkedList<>(original.globalStructs);
			currentStruct = original.currentStruct;
			localVars = new LinkedList<>();
			break;
		case LOCAL:
			globalVars = new LinkedList<>(original.globalVars);
			globalFuncs = new LinkedList<>(original.globalFuncs);
			globalStructs = new LinkedList<>(original.globalStructs);
			currentStruct = original.currentStruct;
			localVars = new LinkedList<>(original.localVars);
			break;
		default:
			System.err.println("No case set for scope: "+scope);
			globalVars = new LinkedList<>();
			globalFuncs = new LinkedList<>();
			globalStructs = new LinkedList<>();
			localVars = new LinkedList<>();
		}
	}
	
	public IDDeclaration findIDDeclaration(String id) throws DeclarationException {
		for(IDDeclaration localVar : localVars) {
			if(localVar.id.equals(id))
				return localVar;
		}
		if (currentStruct != null) {
			for(IDDeclaration structVar : currentStruct.structVars) {
				if(structVar.id.equals(id))
					return structVar;
			}
		}
		for(IDDeclaration globalVar : globalVars) {
			if(globalVar.id.equals(id))
				return globalVar;
		}
		throw new DeclarationException("No variable with id: "+id+ " is currently defined.");
	}
	
	public int addIDDeclaration(String id, Type type, Scope scope) throws DeclarationException {
		switch(scope) {
		case GLOBAL:
			globalVars.add(new IDDeclaration(id, type, scope, globalVars.size()));
			return globalVars.size()-1;
		case LOCAL:
			localVars.add(new IDDeclaration(id, type, scope, localVars.size()));
			return localVars.size()-1;
		case STRUCT:
			if (currentStruct != null)
				return currentStruct.findIDDeclaration(id).offset;
		default:
			System.err.println("No case set for scope: "+scope);
			return 0;
		
		}
	}
	
	public FunDeclaration findFunDeclaration(String id) throws DeclarationException {
		if (currentStruct != null) {
			for(FunDeclaration structFun : currentStruct.structFuncs) {
				if(structFun.id.equals(id))
					return structFun;
			}
		}
		for(FunDeclaration globalFunc : globalFuncs) {
			if(globalFunc.id.equals(id))
				return globalFunc;
		}
		throw new DeclarationException("No function with id: " + id + " has been declared.");
	}

	public String addFunDeclaration(String id, FunctionType funtype, Scope scope) throws DeclarationException {
		switch(scope) {
		case GLOBAL:
			globalFuncs.add(new FunDeclaration(id, funtype, "F"+id));
			return id;
		case STRUCT:
			if (currentStruct != null)
				return currentStruct.findFunDeclaration(id).branchAddress;
		default:
			System.err.println("Function could not be declared at scope: "+scope);
			return "ERRRRRRRROOOORRRR";
		
		}
	}
	
	public String addStructDeclaration(String id, VarDeclNode[] attributes, FunDeclNode[] functions, Type[] cArgTypes) {
		List<IDDeclaration> structVars = new LinkedList<>();
		for(VarDeclNode attribute : attributes) {
			structVars.add(new IDDeclaration(attribute.id, attribute.type, Scope.STRUCT, structVars.size()));
		}
		List<FunDeclaration> structFuncs = new LinkedList<>();
		for(FunDeclNode function : functions) {
			structFuncs.add(new FunDeclaration(function.id, function.funtype, "F"+id+structFuncs.size()));
		}
		globalStructs.add(new StructDeclaration(id,currentStruct = new StructType(id, structVars, structFuncs), cArgTypes, "C"+globalStructs.size()));
		return "C"+(globalStructs.size()-1);
	}

	public StructDeclaration findStructDeclaration(String id) throws DeclarationException {
		for(StructDeclaration globalStruct : globalStructs) {
			if(globalStruct.id.equals(id))
				return globalStruct;
		}
		throw new DeclarationException("No function with id: " + id + " has been declared.");
	}
}
