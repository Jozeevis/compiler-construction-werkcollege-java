package tree.ast.expressions;

import tree.ast.IDDeclarationBlock;

/**
 *
 * @author Loes Kruger s1001459 and Geertje Peters Rit s1000509
 */
public abstract class TwoArg extends BaseExpr{
    protected BaseExpr left;
    protected BaseExpr right;

    public TwoArg(BaseExpr left, BaseExpr right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
	public boolean checkTypes(IDDeclarationBlock domain) {
		return left.checkTypes(domain) & right.checkTypes(domain);
	}
    
    public BaseExpr getLeft() {
    	return left;
    }
    public BaseExpr getRight() {
    	return right;
    }
    
}
