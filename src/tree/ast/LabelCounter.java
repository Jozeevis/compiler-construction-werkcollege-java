/**
 * 
 */
package tree.ast;

/**
 * A simple data object used to keep track of the label count.
 * @author Flip van Spaendonck
 *
 */
public class LabelCounter {

	/** The counter itself **/
	private int count = 0;
	
	public void incr() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
}
