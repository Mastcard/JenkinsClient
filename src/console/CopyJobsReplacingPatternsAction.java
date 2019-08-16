package console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.Constants;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import engine.JobManager;
import exception.CantRetrieveJobsException;
import exception.EmptyRegexpException;
import exception.JobCreationFailedException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoPatternsException;
import exception.NullServerJenkinsException;
import model.Action;
import model.Pattern;

/**
 * The class CopyJobsReplacingPatterns.
 * 
 * @author mastcard
 *
 */
public class CopyJobsReplacingPatternsAction extends Action {

	/** The jobs regexp. */
	private String jobsRegexp;

	/** The job manager. */
	private JobManager jobManager;
	
	/** The patterns. */
	private List<Pattern> patterns = new ArrayList<Pattern>();
	
	/** The jobs to copy. */
	private Map<String, Job> jobsToCopy = new HashMap<String, Job>();
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param args
	 * @throws NullServerJenkinsException 
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer) throws NullServerJenkinsException {
		super();
		jobManager = new JobManager(jenkinsServer);
	}
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param jobRegexp
	 * @throws EmptyRegexpException 
	 * @throws IOException 
	 * @throws NullServerJenkinsException 
	 * @throws CantRetrieveJobsException 
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer,
			String jobRegexp) throws EmptyRegexpException,
			NullServerJenkinsException, CantRetrieveJobsException {
		
		this(jenkinsServer);
		this.jobsRegexp = jobRegexp;
		this.jobsToCopy = jobManager.getJobsWithRegexp(jobRegexp);
	}
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param jobRegexp
	 * @param patterns
	 * @throws EmptyRegexpException 
	 * @throws IOException 
	 * @throws NullServerJenkinsException 
	 * @throws CantRetrieveJobsException 
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer,
			String jobRegexp, List<Pattern> patterns) throws EmptyRegexpException, NullServerJenkinsException, CantRetrieveJobsException {
		
		this(jenkinsServer, jobRegexp);
		this.patterns = patterns;
	}

	/**
	 * Execute.
	 * @throws NoJobsToCopyException 
	 * @throws JobNotFoundException 
	 * @throws IOException 
	 * @throws NoPatternsException 
	 */
	@Override
	public void run() throws NoJobsToCopyException, JobNotFoundException, NoPatternsException {
		check();
		
		for (Iterator<Job> it = jobsToCopy.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			System.out.println("Copying \"" + job.getName() + "... ");
			String newJobName;
			try {
				newJobName = jobManager.copyJobReplacingPatterns(job, patterns, true);
				System.out.println("\tDone : \"" + newJobName + "\"");
			} catch (JobCreationFailedException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Preview.
	 * @throws JobNotFoundException 
	 * @throws IOException 
	 * @throws NoJobsToCopyException 
	 * @throws NoPatternsException 
	 */
	@Override
	public String preview() throws JobNotFoundException, NoJobsToCopyException, NoPatternsException {
		check();
		
		String preview = Constants.CJRP_RESULT_PREVIEW_MESSAGE;
		int higherJobNameSize = getHigherJobNameSize();
		
		for (Iterator<Job> it = jobsToCopy.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			String newJobName = null;
			try {
				newJobName = jobManager.copyJobReplacingPatterns(job, patterns, false);
			} catch (JobCreationFailedException e) {
			}
			
			preview += "\t" + job.getName();
			
			for (int i = job.getName().length(); i < higherJobNameSize + 1; i++) {
				preview += " ";
			}
			
			preview += "====> " + newJobName + "\n";
		}
		
		return preview;
	}
	
	/**
	 * Adds pattern.
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
	 * @throws EmptyRegexpException 
	 * @throws CantRetrieveJobsException 
	 * @throws IOException 
	 */
	public void setJobsRegexp(String jobsRegexp) throws EmptyRegexpException, CantRetrieveJobsException {
		this.jobsRegexp = jobsRegexp;
		this.jobsToCopy = jobManager.getJobsWithRegexp(jobsRegexp);
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
	
	/**
	 * Gets the higher job name size.
	 * 
	 * @return the higher job name size
	 */
	private int getHigherJobNameSize() {
		int higherSize = 0;
		
		for (Iterator<Job> it = jobsToCopy.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			int jobNameSize = job.getName().length();
			
			if (jobNameSize > higherSize) {
				higherSize = jobNameSize;
			}
		}
		
		return higherSize;
	}
	
	/**
	 * Check.
	 * @throws NoJobsToCopyException 
	 * @throws NoPatternsException 
	 */
	private void check() throws NoJobsToCopyException, NoPatternsException {
		if (jobsToCopy.isEmpty()) {
			throw new NoJobsToCopyException();
		}
		if (patterns.isEmpty()) {
			throw new NoPatternsException();
		}
	}
	
}
