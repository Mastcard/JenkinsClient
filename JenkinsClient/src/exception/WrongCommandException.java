package exception;

/**
 * The class WrongCommandException.
 * 
 * @author mastcard
 *
 */
public class WrongCommandException extends Exception {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new WrongCommandException.
	 */
	public WrongCommandException() {
		message = "The command is not correct.";
	}
	
	/**
	 * @see java.lang.Object#toString();
	 */
	public String toString() {
		return message;
	}
	
}
