package tree.ast.expressions.num;

import java.util.Map;

import tree.ast.IDDeclarationBlock;
import tree.ast.expressions.NoArg;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public class NumConstant extends NoArg{
    public final int constant;

    public NumConstant(int val) {
        this.constant = val;
    }

    @Override
    public String toString() {
        return ""+ constant;
    }

	@Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return true;
	}

	@Override
	public String getCode() {
		return "ldc "+constant;
	}
    
    
}
