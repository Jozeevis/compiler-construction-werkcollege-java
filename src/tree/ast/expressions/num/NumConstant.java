package tree.ast.expressions.num;

import java.util.Map;

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
    
    
}
