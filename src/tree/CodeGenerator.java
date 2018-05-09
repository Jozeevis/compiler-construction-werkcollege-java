/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;

import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.OneArg;
import tree.ast.expressions.TwoArg;

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
		List<BaseExpr> frontier = new LinkedList<>();
		frontier.add(expression);
		while(!frontier.isEmpty()) {
			BaseExpr current = frontier.remove(0);
			out.add(current.getCode());
			if (current instanceof TwoArg) {
				frontier.add(((TwoArg) current).getRight());
				frontier.add(((TwoArg) current).getLeft());
			} else if (current instanceof OneArg) {
				frontier.add(((OneArg) current).getValue());
			} else {
				//Check what kind of Expr this is, twoArg, oneArg, noArg and add the code to the list.
			}
		}
		return out;
	}
}
