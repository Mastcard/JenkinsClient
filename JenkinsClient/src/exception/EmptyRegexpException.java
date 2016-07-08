package exception;

/**
 * The class EmptyRegexpException.
 * 
 * @author mastcard
 *
 */
public class EmptyRegexpException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new EmptyRegexpException.
	 */
	public EmptyRegexpException() {
		super("The regular expression is null or empty");
	}

}
