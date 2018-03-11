package grammar;

import java.util.LinkedList;
import java.util.List;

/**
 * The node class is used to represent expression declerations in the syntax tree.
 * 	A Node contains a list of the possible expressions it can be.
 * @author Flip van Spaendonck
 */
public class Node {
	/** The */
	protected final List<Expression> expressions;
	/** The name/id used by this node.*/
	public final String id;
	
	/**
	 * Creates a node with the given name.
	 * @param name the name/id used by this node.
	 * In a tree a name/id should be unique, this should however be checked by the tree itself. It should also only contains alpha-numerical characters, and may not end with Star or Plus.
	 */
	public Node(String name) {
		id = name;
		expressions = new LinkedList<Expression>();
	};
	
	@Override
	public String toString() {
		return id;
	}
	
	
}
