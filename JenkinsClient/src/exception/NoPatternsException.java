package exception;

/**
 * The class NoPatternsException.
 * 
 * @author mastcard
 *
 */
public class NoPatternsException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new NoPatternsException.
	 */
	public NoPatternsException() {
		super("There is no pattern to replace.");
	}
	
}
