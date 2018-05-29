package tree.ast.expressions;

import tree.ast.IDDeclarationBlock;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public abstract class OneArg extends BaseExpr{
    protected BaseExpr val;

    public OneArg(BaseExpr val) {
        this.val = val;
    }

    public BaseExpr getValue() {
    	return val;
    }
    
}
