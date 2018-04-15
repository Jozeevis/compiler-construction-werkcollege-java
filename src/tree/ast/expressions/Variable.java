package tree.ast.expressions;

import java.util.List;
import java.util.Map;

import tree.IDDeclaration;
import tree.ast.types.Type;

/**
 *@author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class Variable extends NoArg {

    private String variable;
    /** The type that this variable is expected to have. **/
    public final Type expectedType;
    
    public Variable(String variable, Type expectedType) {
        this.variable = variable;
        this.expectedType = expectedType;
    }

    @Override
    public String toString() {
        return variable;
    }

	@Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		for(IDDeclaration declaration : domain) {
			if (declaration.id.equals(variable))
				return declaration.type.equals(expectedType);
		}
		return false;
	}

}
