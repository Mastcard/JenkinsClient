package exception;

/**
 * The class CantRetrieveJobsException.
 * 
 * @author mastcard
 *
 */
public class CantRetrieveJobsException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new CantRetrieveJobsException.
	 */
	public CantRetrieveJobsException() {
		super("An error occured while trying to retrieve jobs on Jenkins server");
	}
	
}
