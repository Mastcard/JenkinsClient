package exception;

/**
 * The class JobAlreadyExistsException.
 * 
 * @author mastcard
 *
 */
public class JobCreationFailedException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new JobAlreadyExistsException.
	 * 
	 * @param jobName
	 */
	public JobCreationFailedException(String jobName) {
		super("The job \"" + jobName + "\" creation has failed.\n"
				+ "The cause of the issue could be one of the followings :\n"
				+ "\t1) a job with the same name already exists\n"
				+ "\t2) the xml config file of the job was empty");
	}

}
