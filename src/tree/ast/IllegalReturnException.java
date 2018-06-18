/**
 * 
 */
package tree.ast;

/**
 * @author Flip van Spaendonck
 *
 */
public class IllegalReturnException extends Exception {

	public IllegalReturnException() {
		super("'return' encountered outside of function declaration.");
	}
}
