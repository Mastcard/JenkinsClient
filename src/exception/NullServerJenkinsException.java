package exception;

/**
 * The class NullServerJenkinsException.
 * 
 * @author mastcard
 *
 */
public class NullServerJenkinsException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new NullServerJenkinsException.
	 */
	public NullServerJenkinsException() {
		super("The JenkinsServer object is null.");
	}
	
}
