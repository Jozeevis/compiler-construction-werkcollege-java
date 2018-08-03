package grammar;

import lexer.LexingException;

/**
 * The programmatic representation of Node*.
 * @author Flip van Spaendonck
 */
public class StarNode extends Node {

	public StarNode(String originalNodeName, ExpressionTree syntax) {
		super( originalNodeName +"Star");
		expressions.add(new Expression( new Object[] {syntax.getNode(originalNodeName), this}));
		try {
			expressions.add(new Expression(".TOK_NIL", syntax));
		} catch (LexingException e) {
			//No lexing exception should be able to occur during the above part, or there is something wrond with the lexer.
			e.printStackTrace();
		}
	}
}
