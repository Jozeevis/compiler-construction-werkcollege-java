package tree.ast.expressions;

import java.util.List;
import java.util.Map;

import tree.IDDeclaration;
import tree.ast.IDDeclarationBlock;
import tree.ast.types.Type;

/**
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Variable extends NoArg {

    private String variable;
    /** The type that this variable is expected to have. **/
    public final Type expectedType;
    private int linkNumber;
    
    public Variable(String variable, Type expectedType) {
        this.variable = variable;
        this.expectedType = expectedType;
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
	public void addCodeToStack(List<String> stack) {
		stack.add("ldl "+ linkNumber);
		stack.add("ldh 0");
	}

}
