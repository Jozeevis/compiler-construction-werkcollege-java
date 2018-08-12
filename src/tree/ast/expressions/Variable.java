package tree.ast.expressions;

import java.util.LinkedList;
import java.util.List;

import lexer.TokenField;
import lexer.TokenIdentifier;
import lexer.TokenTupleFunction;
import lexer.TokenType;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclaration;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxNode;
import tree.IDDeclarationBlock.Scope;
import tree.ast.LabelCounter;
import tree.ast.accessors.Accessor;
import tree.ast.accessors.ListHeadAccessor;
import tree.ast.accessors.ListTailAccessor;
import tree.ast.accessors.StructVarAccessor;
import tree.ast.types.Type;

/**
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Variable extends NoArg {

	private String variable;
	private int linkNumber;

	private Scope scope;
	
	public Variable(String variable) {
		this.variable = variable;
	}

	public String getID() {
		return variable;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws DeclarationException, TypeException {
		IDDeclaration varDef = domain.findIDDeclaration(variable);
		linkNumber = varDef.offset;
		scope = varDef.scope;
		return varDef.type;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		switch(scope) {
		case GLOBAL:
			stack.add("ldl 1\n");
			stack.add("ldh "+ (-linkNumber) + "\n");
			break;
		case STRUCT:
			stack.add("ldl 2\n");
			stack.add("ldh "+ (-linkNumber) + "\n");
			break;
		case LOCAL:
			stack.add("ldl "+(3+linkNumber) + "\n");
			break;
		
		default:
			System.err.println("No case defined for scope: "+scope);
			break;
		}
	}

}
