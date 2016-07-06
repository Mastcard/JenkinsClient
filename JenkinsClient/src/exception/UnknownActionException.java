package exception;

/**
 * The class UnknownActionException.
 * 
 * @author mastcard
 *
 */
public class UnknownActionException extends Exception {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new UknownActionException.
	 * 
	 * @param actionArg
	 */
	public UnknownActionException(String actionArg) {
		message = "The action " + actionArg + " is not correct or not yet supported.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
