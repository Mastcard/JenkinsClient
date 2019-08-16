package exception;

/**
 * The class JenkinsNotRunningException.
 * 
 * @author mastcard
 *
 */
public class JenkinsConnectionFailedException extends JenkinsClientException {

	/** The default Serial UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new JenkinsNotRunningException.
	 * 
	 * @param server
	 */
	public JenkinsConnectionFailedException(String server) {
		super("The connection to the server \"" + server + "\" failed.\nCheck your server name, your credentials, and retry.");
	}

}
