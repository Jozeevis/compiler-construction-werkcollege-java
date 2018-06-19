package tree;

import java.util.LinkedList;
import java.util.List;

import tree.ast.LabelCounter;

/**
 * @author Flip van Spaendonck and Lars Kuijpers
 *
 */
public class CodeGenerator {
	
	public static List<String> generateCode(SyntaxTree tree) {
		/** Contains the full code that will be generated. */
		List<String> stack = new LinkedList<String>();
		/** A counter used to keep track of what label we're at. */
		LabelCounter counter = new LabelCounter();
		stack.add("link 2");
		for(int i=0; i<tree.nrOfGlobals; i++)
			stack.add("ldc 0");
		stack.add("stmh 0 "+tree.nrOfGlobals);
		stack.add("stl 1");
		stack.add("bsr "+tree.mainAddress);
		stack.add("halt");
		tree.root.addCodeToStack(stack, counter);
		return stack;
	}
	
	public static List<String> generateCode(SyntaxNode node) {
		/** Contains the full code that will be generated. */
		List<String> stack = new LinkedList<String>();
		/** A counter used to keep track of what label we're at. */
		LabelCounter counter = new LabelCounter();
		node.addCodeToStack(stack, counter);
		return stack;
	}
}
