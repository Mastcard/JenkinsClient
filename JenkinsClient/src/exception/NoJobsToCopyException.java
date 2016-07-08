package exception;

/**
 * The class NoJobsToCopyException.
 * 
 * @author mastcard
 *
 */
public class NoJobsToCopyException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new NoJobsToCopyException.
	 */
	public NoJobsToCopyException() {
		super("There's no job to copy with this regular expression.");
	}

}
