package exception;

/**
 * The class CantRetrieveViewException.
 * 
 * @author mastcard
 *
 */
public class CantRetrieveViewsException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new CantRetrieveViewsException. 
	 */
	public CantRetrieveViewsException() {
		super("An error occured while trying to retrive views on Jenkins server.");
	}
	
}
