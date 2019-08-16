package model;

/**
 * The class Pattern.
 * 
 * @author mastcard
 *
 */
public class Pattern {

	/** The before. */
	private String before;
	
	/** The after. */
	private String after;
	
	/**
	 * Instantiates a new Pattern.
	 * 
	 * @param before
	 * @param after
	 */
	public Pattern(String before, String after) {
		this.setBefore(before);
		this.setAfter(after);
	}

	/**
	 * Gets the before.
	 * 
	 * @return the before
	 */
	public String getBefore() {
		return before;
	}

	/**
	 * Sets the before.
	 * 
	 * @param before
	 */
	public void setBefore(String before) {
		this.before = before;
	}

	/**
	 * Gets the after.
	 * 
	 * @return the after
	 */
	public String getAfter() {
		return after;
	}

	/**
	 * Sets the after.
	 * 
	 * @param after
	 */
	public void setAfter(String after) {
		this.after = after;
	}
	
}
