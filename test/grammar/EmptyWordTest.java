/**
 * 
 */
package grammar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * @author Flip van Spaendonck
 *
 */
public class EmptyWordTest {

	@Test
	public void testEmptyWord() {
		assertTrue(EmptyWord.NIL.toString().equals("NIL"));
	}
	
	
}
