package exception;

/**
 * The class NoJobsToRemoveException.
 * 
 * @author mastcard
 *
 */
public class NoJobsToRemoveException extends JenkinsClientException {

	/** The default Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new NoJobsToRemoveException.
	 */
	public NoJobsToRemoveException() {
		super("There's no job to remove with this regular expression.");
	}

}
