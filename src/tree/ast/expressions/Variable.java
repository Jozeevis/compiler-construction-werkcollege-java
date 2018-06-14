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
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxNode;
import tree.ast.IDDeclarationBlock;
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

	public Variable(String variable) {
		this.variable = variable;
	}

	public String getID() {
		return variable;
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws DeclarationException, TypeException {
		Type innerType = null;
		for (int i = domain.block.length - 1; i >= 0; i--) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(variable)) {
				linkNumber = i + 1;
				innerType = declaration.type;
				break;
			}
		}
		if (innerType == null)
			throw new DeclarationException("Variable with id: " + variable + " has not yet been declared.");
		return innerType;
	}

	public int getLinkNumber() {
		return linkNumber;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldl " + linkNumber);
		stack.add("ldh 0");
	}

}
