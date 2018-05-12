package tree.ast.expressions;

import tree.ast.IDDeclarationBlock;

/**
 *
 * @author Loes Kruger and Geertje Peters Rit
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
