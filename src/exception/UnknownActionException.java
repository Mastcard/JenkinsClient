package exception;

/**
 * The class UnknownActionException.
 * 
 * @author mastcard
 *
 */
public class UnknownActionException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new UknownActionException.
	 * 
	 * @param actionArg
	 */
	public UnknownActionException(String actionArg) {
		super("The action " + actionArg + " is not correct or not yet supported.");
	}

}
