package exception;

/**
 * The class NullServerNameException.
 * 
 * @author mastcard
 *
 */
public class NullServerNameException extends JenkinsClientException {

	/** The Serial version UID.*/
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new NullServerNameException.
	 */
	public NullServerNameException() {
		super("There's no server information given in the command.");
	}
	
}
