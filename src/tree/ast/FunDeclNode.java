package tree.ast;

import java.util.List;

import lexer.TokenIdentifier;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxNode;
import tree.IDDeclarationBlock.Scope;
import tree.ast.types.CustomType;
import tree.ast.types.Type;
import tree.ast.types.specials.FunctionType;
import tree.ast.types.specials.VoidType;

/**
 * An abstract syntax knot representing a function declaration.
 * 
 * @author Lars Kuijpers and Flip van Spaendonck
 */
public class FunDeclNode extends ASyntaxKnot {

	/** The identifier of the function **/
	public final String id;
	/** The identifiers of the arguments of the function **/
	public final String[] funArgs;
	/** The type of the function **/
	public final FunctionType funtype;
	/** The variables that are declared at the start of the function body **/
	public final VarDeclNode[] varDecls;
	/** The label used in the SSM code **/
	private String branchAddress;
	/** The TokenExpression that denotes the function body **/
	public final SyntaxNode body;

	public FunDeclNode(SyntaxExpressionKnot oldKnot, SyntaxKnot frontier) throws Exception {
		super(frontier);
		id = ((TokenIdentifier) oldKnot.children[0].reduceToToken()).getValue();
		if (oldKnot.children.length == 9) { // Function declaration without Function arguments
			// "~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
			funArgs = new String[0];
			funtype = ExtractFunctionType((SyntaxExpressionKnot) oldKnot.children[4]);
			varDecls = ExtractVariables((SyntaxExpressionKnot) oldKnot.children[6]);
			body = oldKnot.children[7].getASTEquivalent(this);
		} else { // Function declaration with Function arguments
					// "~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl"
					// Get identifiers of the function arguments out of the FArgs expression
			funtype = ExtractFunctionType((SyntaxExpressionKnot) oldKnot.children[5]);
			//TODO: funArgs has to be parsed without inferring to funtype, at the moment no error is thrown if there are more funargs then inputtypes.
			funArgs = new String[funtype.inputTypes.length];
			SyntaxExpressionKnot fArgKnot = (SyntaxExpressionKnot) oldKnot.children[2];
			int i = 0;
			while (fArgKnot.children.length != 1) {
				funArgs[i] = ((TokenIdentifier) fArgKnot.children[0].reduceToToken()).value;
				fArgKnot = (SyntaxExpressionKnot) fArgKnot.children[2];
				i++;
			}
			funArgs[i] = ((TokenIdentifier) fArgKnot.children[0].reduceToToken()).value;
			varDecls = ExtractVariables((SyntaxExpressionKnot) oldKnot.children[7]);
			body = oldKnot.children[8].getASTEquivalent(this);
		}
		if (!(funtype.returnType instanceof VoidType)) {
			if (!body.alwaysReturns())
				throw new Exception("Function "+id+" does not always hit a return statement.");
		}
		children = new SyntaxNode[] { body };
	}

	/**
	 * Extracts all variable declarations out of the given syntax knot and returns
	 * them as an array
	 */
	private VarDeclNode[] ExtractVariables(SyntaxExpressionKnot vardecls) {
		List<SyntaxNode> nodes = TreeProcessing.extractFromStarNode(vardecls);
		VarDeclNode[] variables = new VarDeclNode[nodes.size()];
		for(int i=0; i< variables.length; i++) {
			variables[i] = new VarDeclNode((SyntaxExpressionKnot) nodes.get(i), this);
		}
		return variables;
	}

	/**
	 * Extracts the FunctionType from the given SyntaxKnot and returns it
	 */
	private FunctionType ExtractFunctionType(SyntaxExpressionKnot funtype) {
		List<SyntaxNode> typeNodes = TreeProcessing.extractFromStarNode((SyntaxKnot) funtype.children[0]);
		Type[] left = new Type[typeNodes.size()];
		int counter = 0;
		for (SyntaxNode typeNode : typeNodes) {
			left[counter] = Type.inferType((SyntaxExpressionKnot) typeNode);
			counter++;
		}
		Type right;
		if (((SyntaxKnot) funtype.children[2]).children[0] instanceof SyntaxExpressionKnot) {
			right = Type.inferType((SyntaxExpressionKnot) ((SyntaxKnot) funtype.children[2]).children[0]);
		} else {
			right = VoidType.instance;
		}
		return new FunctionType(left, right);
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		branchAddress = domain.addFunDeclaration(id, funtype, scope);
		IDDeclarationBlock temp = new IDDeclarationBlock(domain, Scope.LOCAL);
		for(Type type : funtype.inputTypes) {
			if (type instanceof CustomType) {
				domain.findStructDeclaration(((CustomType) type).typeName);
			}
		}
		if (funtype.returnType instanceof CustomType) {
			domain.findStructDeclaration(((CustomType)funtype.returnType).typeName);
		}
		if(funArgs.length != funtype.inputTypes.length) {
			throw new DeclarationException("This function declaration has "+funArgs.length+" nr. of arguments, while it has "+funtype.inputTypes.length+" nr. of input-types.");
		}
		for(int i=0; i<funArgs.length; i++) {
			temp.addIDDeclaration(funArgs[i], funtype.inputTypes[i], Scope.LOCAL);
		}
		for(VarDeclNode varDecl : varDecls) {
			varDecl.checkTypes(temp, Scope.LOCAL);
		}
		body.checkTypes(temp, Scope.LOCAL);
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// Label to skip the function body if the code comes here some other way
		stack.add("bra " + branchAddress + "Skip\n");
		// Label to jump to the function body
		stack.add(branchAddress + ": ldl 1\n");
		stack.add("str 6\n");
		stack.add("ldl 2\n");
		stack.add("str 7\n");
		stack.add("link " + (funArgs.length + varDecls.length+2) + "\n");
		stack.add("ldr 6\n");
		stack.add("stl 1\n");
		stack.add("ldr 7\n");
		stack.add("stl 2\n");
		stack.add("ldr 5\n");
		stack.add("ldmh 0 " +funArgs.length + "\n");
		stack.add("stml 3 " + funArgs.length + "\n");
		// Generate the code for the variable declarations at the beginning of the code
		// body.
		for (VarDeclNode varDeclaration : varDecls) {
			varDeclaration.addCodeToStack(stack, counter);
		}
		// Generate the code of the function body
		body.addCodeToStack(stack, counter);
		// Void functions don't need a return type, so we'll add one in just in case
		if (funtype.returnType instanceof VoidType) {
			stack.add("unlink\n");
			stack.add("ret\n");
		}
		// Label for the skip to go to
		stack.add(branchAddress + "Skip: nop\n");
	}
	
	@Override
	public String toString() {
		return "funDecl:"+id;
	}

	@Override
	public boolean alwaysReturns() {
		return false;
	}

}
