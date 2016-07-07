package exception;

/**
 * The class NoJobsToCopyException.
 * 
 * @author mastcard
 *
 */
public class NoJobsToCopyException extends Exception {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new NoJobsToCopyException.
	 */
	public NoJobsToCopyException() {
		message = "There's no job to copy with this regular expression.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
