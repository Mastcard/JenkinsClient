package exception;

/**
 * The class JenkinsNotRunningException.
 * 
 * @author mastcard
 *
 */
public class JenkinsConnectionFailedException extends Exception {

	/** The default Serial UID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new JenkinsNotRunningException.
	 * 
	 * @param server
	 */
	public JenkinsConnectionFailedException(String server) {
		super();
		message = "The connection to the server \"" + server + "\" failed.\nCheck your server name, your credentials, and retry.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
