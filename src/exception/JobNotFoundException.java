package exception;

/**
 * The class JobNotFoundException.
 * 
 * @author mastcard
 *
 */
public class JobNotFoundException extends JenkinsClientException {

	/** The default SERIAL_ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new JobNotFoundException.
	 * 
	 * @param jobName
	 */
	public JobNotFoundException(String jobName) {
		super("The job \"" + jobName + "\" has not been found.");
	}

}
