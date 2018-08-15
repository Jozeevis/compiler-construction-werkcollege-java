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
		
		stack.add("link 2\n");
		for(int i=0; i<tree.nrOfGlobals; i++)
			stack.add("ldc 0\n");
		stack.add("stmh "+tree.nrOfGlobals+ "\n");
		stack.add("stl 1\n");
		tree.root.addCodeToStack(stack, counter);
		stack.add("bsr "+tree.mainAddress+ "\n");
		stack.add("halt\n");
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
