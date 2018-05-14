package tree.ast.expressions;

import java.util.LinkedList;
import java.util.List;

import lexer.TokenField;
import tree.IDDeclaration;
import tree.SyntaxExpressionKnot;
import tree.ast.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.Type;

/**
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Variable extends NoArg {

    private String variable;
    private final TokenField[] accessors;
    /** The type that this variable is expected to have. **/
    public final Type expectedType;
    private int linkNumber;
    
    public Variable(String variable, SyntaxExpressionKnot fieldStar, Type expectedType) {
        this.variable = variable;
        accessors = extractFieldTokens(fieldStar);
        this.expectedType = expectedType;
    }
    
    private static TokenField[] extractFieldTokens(SyntaxExpressionKnot fieldStar) {
		List<TokenField> tokens = new LinkedList<>(); 
		SyntaxExpressionKnot currentKnot = fieldStar;
		while (currentKnot.children.length == 2) {
			tokens.add((TokenField) currentKnot.children[0].reduceToToken());
			currentKnot = (SyntaxExpressionKnot)currentKnot.children[1];
		}
		return (TokenField[]) tokens.toArray();
	}

    
    public String getID() {
        return variable;
    }

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		for(int i=domain.block.length-1; i>=0; i--) {
			IDDeclaration declaration = domain.block[i];
			if (declaration.id.equals(variable)) {
				linkNumber = i + 1;
				return declaration.type.equals(expectedType);
			}
		}
		return false;
	}

	public int getLinkNumber() {
		return linkNumber;
	}
	
	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		stack.add("ldl "+ linkNumber);
		for(TokenField accessor : accessors) {
			accessor.addCodeToStack(stack);
		}
		stack.add("ldh 0");
	}

}
