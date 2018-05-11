/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;
import tree.ast.expressions.TupleExp;
import tree.ast.expressions.TwoArg;
import tree.ast.expressions.Variable;

/**
 * @author Flip van Spaendonck
 *
 */
public class CodeGenerator {

	public static List<String> generateCode(SyntaxTree tree) {
		
		return null;
	}
	
	public static List<String> generateCode(BaseExpr expression) {
		List<String> out = new LinkedList<>();
		expression.addCodeToStack(out);
		return out;
	}
}
