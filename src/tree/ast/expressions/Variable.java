package tree.ast.expressions;

import java.util.Map;

/**
 *@author Loes Kruger s1001459 and Geertje Peters Rit s1000509
 */
public class Variable extends NoArg {

    private String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable;
    }

}
