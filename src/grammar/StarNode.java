package grammar;

/**
 * The programmatic representation of Node*.
 * @author Flip van Spaendonck
 */
public class StarNode extends Node {

	public StarNode(String originalNodeName, ExpressionTree syntax) {
		super( originalNodeName +"Star");
		expressions.add(new Expression( new Object[] {syntax.getNode(originalNodeName), this}));
		expressions.add(new Expression("null", syntax));
	}
}
