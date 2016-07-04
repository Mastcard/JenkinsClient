package exception;

/**
 * The class JobNotFoundException.
 * 
 * @author mastcard
 *
 */
public class JobNotFoundException extends Exception {

	/** The default SERIAL_ID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new JobNotFoundException.
	 * 
	 * @param jobName
	 */
	public JobNotFoundException(String jobName) {
		super();
		message = "The job \"" + jobName + "\" has not been found.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
