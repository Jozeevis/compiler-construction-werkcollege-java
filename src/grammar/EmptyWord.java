package grammar;

/**
 *  This is a singleton class used for the empty word.
 * @author Flip van Spaendonck
 *
 */
public final class EmptyWord {

	/** The singleton field */
	public static final EmptyWord NIL = new EmptyWord();
	
	private EmptyWord() {};
	
	@Override
	public String toString() {
		return "NIL";
	}
}
