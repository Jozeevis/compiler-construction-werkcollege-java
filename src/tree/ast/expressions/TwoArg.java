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
    
    public BaseExpr getLeft() {
    	return left;
    }
    public BaseExpr getRight() {
    	return right;
    }
    
}
