package tree.ast.expressions;

import java.util.List;

import tree.IDDeclaration;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public abstract class OneArg extends BaseExpr{
    protected BaseExpr val;

    public OneArg(BaseExpr val) {
        this.val = val;
    }
    
    @Override
	public boolean checkTypes(List<IDDeclaration> domain) {
		return val.checkTypes(domain);
	}

    public BaseExpr getValue() {
    	return val;
    }
    
}
