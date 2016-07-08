package exception;

/**
 * The class SevralJobsWithSameNameInViewException.
 * This exception is thrown when, for instance, a job name is showed in a ListView and in a Tests Result view, on the same MainView.
 * 
 * @author mastcard
 *
 */
public class SeveralJobsWithSameNameInViewException extends IllegalArgumentException {

	/** The Default SERIAL_ID. */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new SeveralJobsWithSameNameInViewException
	 * 
	 * @param view
	 * @param job
	 */
	public SeveralJobsWithSameNameInViewException(String view) {
		super();
		message = "There's several jobs with the same name in the view \"" + view + "\".\nWe can't show unused jobs in this case.";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return message;
	}

}
