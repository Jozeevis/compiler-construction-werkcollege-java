/**
 * 
 */
package tree.ast.expressions;

/**
 * @author Flip van Spaendonck
 *
 */
public class IllegalThisException extends Exception {

	public IllegalThisException() {
		super("'this' encountered outside of struct declaration.");
	}
}
