package exception;

/**
 * The class WrongCommandException.
 * 
 * @author mastcard
 *
 */
public class WrongCommandException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new WrongCommandException.
	 */
	public WrongCommandException() {
		super("The command is not correct.");
	}

}
