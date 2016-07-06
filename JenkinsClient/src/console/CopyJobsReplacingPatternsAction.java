package console;

import java.util.ArrayList;
import java.util.List;

import model.Action;
import model.Pattern;

public class CopyJobsReplacingPatternsAction extends Action {

	/** The jobs regexp. */
	private String jobsRegexp;
	
	/** The patterns. */
	private List<Pattern> patterns = new ArrayList<Pattern>();
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param args
	 */
	public CopyJobsReplacingPatternsAction() {
		super();
	}
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param jobRegexp
	 */
	public CopyJobsReplacingPatternsAction(String jobRegexp) {
		this();
		this.jobsRegexp = jobRegexp;
	}
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param jobRegexp
	 * @param patterns
	 */
	public CopyJobsReplacingPatternsAction(String jobRegexp, List<Pattern> patterns) {
		this(jobRegexp);
		this.patterns = patterns;
	}

	/**
	 * Execute.
	 */
	@Override
	public void run() {
		
	}

	/**
	 * Preview.
	 */
	@Override
	public String preview() {
		return null;
	}
	
	/**
	 * Adds pattern
	 * 
	 * @param pattern
	 */
	public void addPattern(Pattern pattern) {
		patterns.add(pattern);
	}

	/**
	 * Gets the jobs regexp.
	 * 
	 * @return the jobs regexp
	 */
	public String getJobsRegexp() {
		return jobsRegexp;
	}

	/**
	 * Sets the jobs regexp.
	 * 
	 * @param jobsRegexp
	 */
	public void setJobsRegexp(String jobsRegexp) {
		this.jobsRegexp = jobsRegexp;
	}

	/**
	 * Gets the patterns.
	 * 
	 * @return the patterns
	 */
	public List<Pattern> getPatterns() {
		return patterns;
	}

	/**
	 * Sets the patterns.
	 * 
	 * @param patterns
	 */
	public void setPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
	}
	
}
