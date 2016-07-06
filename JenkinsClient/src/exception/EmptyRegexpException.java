package exception;

/**
 * The class EmptyRegexpException.
 * 
 * @author mastcard
 *
 */
public class EmptyRegexpException extends Exception {

	/** The serial verion UID. */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new EmptyRegexpException.
	 */
	public EmptyRegexpException() {
		message = "The regular expression is null or empty";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
