package exception;

/**
 * The class JenkinsClientException.
 * 
 * @author mastcard
 *
 */
public class JenkinsClientException extends Exception {

	/** The Serial version UID. */
	protected static final long serialVersionUID = 1L;

	/** The message. */
	protected String message;

	/**
	 * Instantiates a new JenkinsClientException. 
	 */
	public JenkinsClientException() {
	}
	
	/**
	 * Instantiates a new JenkinsClientException.
	 * 
	 * @param message
	 */
	public JenkinsClientException(String message) {
		this.message = message;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}
	
}
