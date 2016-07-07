package exception;

/**
 * The class JobAlreadyExistsException.
 * 
 * @author mastcard
 *
 */
public class JobAlreadyExistsException extends Exception {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new JobAlreadyExistsException.
	 * 
	 * @param jobName
	 */
	public JobAlreadyExistsException(String jobName) {
		message = "\tThe job \"" + jobName + "\" already exists.\n";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
