package tree.ast.expressions;

import java.util.List;

import tree.IDDeclaration;

/**
 *
 * @author Loes Kruger s1001459 and Geertje Peters Rit s1000509
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

    
}
