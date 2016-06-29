package exception;

/**
 * The class JenkinsNotRunningException.
 * 
 * @author mastcard
 *
 */
public class JenkinsNotRunningException extends Exception {

	/** The default Serial UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new JenkinsNotRunningException.
	 * 
	 * @param server
	 */
	public JenkinsNotRunningException(String server) {
		message = "The server " + server + " is not running or doesn't exist.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
