package tree.ast.expressions;

/**
 *
 * @author Loes Kruger s1001459 and Geertje Peters Rit s1000509
 */
public abstract class OneArg extends BaseExpr{
    protected BaseExpr val;

    public OneArg(BaseExpr val) {
        this.val = val;
    }

    
}
