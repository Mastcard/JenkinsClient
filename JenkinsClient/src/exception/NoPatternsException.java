package exception;

/**
 * The class NoPatternsException.
 * 
 * @author mastcard
 *
 */
public class NoPatternsException extends Exception {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	public NoPatternsException() {
		message = "There is no pattern to replace.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
