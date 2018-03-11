package grammar;

import java.util.LinkedList;
import java.util.List;

/**
 * A data tree structure containing expression nodes. The ExpressionTree is used to represent an abstract syntax. 
 *  The parser can then use this tree to parse a program and check whether it's valid or not.
 * @author Flip van Spaendonck
 *
 */
public abstract class ExpressionTree {
	
	public final Node root;
	private final List<Node> nodes;
	
	protected ExpressionTree(Node root) {
		this.root = root;
		nodes = new LinkedList<Node>();
	}
	
	/**
	 * Adds an expression to one of the nodes in this syntax.
	 */
	protected void addExpressionTo(Expression expression, String parentID) {
		//TODO: If no matching nodes are found, the method should throw a custom exception.
		
		for(Node node : nodes) {
			if (node.id.equals(parentID)) {
				node.expressions.add(expression);
			}
		}
	}
	/**
	 * Adds an expression to one of the nodes in this syntax.
	 */
	protected final void addExpressionTo(String expression, String parentID) {
		
		Expression expr = new Expression(expression, this);
		//TODO: If no matching nodes are found, the method should throw a custom exception.
		for(Node node : nodes) {
			if (node.id.equals(parentID)) {
				node.expressions.add(expr);
			}
		}
	}
		
	
	/**
	 * Adds a node to the list of possible nodes.
	 * @param node
	 */
	protected final void addNode(Node node) {
		//TODO: This method should throw a custom exception if another node with the same ID already exists.
		nodes.add(node);
	}
	
	
	/**
	 * Returns the node from this tree with a matching name/id.
	 * @param name the id of the to-be matched node.
	 * @return a node with a matching id.
	 */
	public Node getNode(String name) {
		//System.out.println("searching for node with name: " + name);
		//TODO: If no matching nodes are found, the method should throw a custom exception.
		for(Node node : nodes) {
			if (node.id.equals(name)) {
				return node;
			}
		}
		return null;
		
	}
	
	/**
	 * Prints the tree to the System.out
	 */
	public void printTree() {
		//TODO: Make this one prettier
		for(Node node : nodes) {
			String out = node.id + " = ";
			for(Expression expression : node.expressions) {
				for(Object o : expression.expression)
					out += o + " ";
				out += " | ";
			}
			System.out.println(out);
		}
	}

	
}
