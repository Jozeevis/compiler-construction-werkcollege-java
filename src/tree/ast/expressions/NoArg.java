package tree.ast.expressions;


/**
 *
 * @author Loes Kruger s1001459 and Geertje Peters Rit s1000509
 */
public abstract class NoArg extends BaseExpr{

    /**
     * A constant or variable can't be optimized so it returns this
     * @return BaseExpr
     */
    @Override
    public BaseExpr optimize() {
        return this;
    }    
    
}
