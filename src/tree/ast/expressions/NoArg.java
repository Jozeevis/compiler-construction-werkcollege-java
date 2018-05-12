package tree.ast.expressions;


/**
 *
 * @author Loes Kruger and Geertje Peters Rit
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
