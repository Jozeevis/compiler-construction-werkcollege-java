package tree;

import java.util.LinkedList;
import java.util.List;

import tree.ast.LabelCounter;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class CodeGenerator {
	
	public static List<String> generateCode(SyntaxNode node) {
		/** Contains the full code that will be generated. */
		List<String> stack = new LinkedList<String>();
		/** A counter used to keep track of what label we're at. */
		LabelCounter counter = new LabelCounter();
		node.addCodeToStack(stack, counter);
		return stack;
	}
}
