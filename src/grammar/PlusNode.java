package grammar;

/**
 * The PlusNode is the programmatic representation of Node+.
 * @author Flip van Spaendonck
 */
public class PlusNode extends Node {

	public PlusNode(String originalNodeName, ExpressionTree syntax) {
		super( originalNodeName +"Plus");
		expressions.add(new Expression( new Object[] {syntax.getNode(originalNodeName), this}));
		expressions.add(new Expression(new Object[] {syntax.getNode(originalNodeName)}));
	}
}
