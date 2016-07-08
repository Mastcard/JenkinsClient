package exception;

/**
 * The class WrongURISyntaxException.
 * 
 * @author I312791
 *
 */
public class WrongURISyntaxException extends JenkinsClientException {

	/** The Serial version UID. */
	private static final long serialVersionUID = 1L;

	public WrongURISyntaxException(String uri) {
		super("The syntax of \"" + uri + " is not correct.");
	}
	
}
