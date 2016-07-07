package console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import engine.JobManager;
import exception.EmptyRegexpException;
import exception.JobAlreadyExistsException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoPatternsException;
import model.Action;
import model.Pattern;

public class CopyJobsReplacingPatternsAction extends Action {

	/** The jobs regexp. */
	private String jobsRegexp;
	
	/** The patterns. */
	private List<Pattern> patterns = new ArrayList<Pattern>();
	
	/** The jobs to copy. */
	private Map<String, Job> jobsToCopy = new HashMap<String, Job>();
	
	/** The job manager. */
	private JobManager jobManager;
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param args
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer) {
		super();
		jobManager = new JobManager(jenkinsServer);
	}
	
	/**
	 * Instantiates a new CopyJobsReplacingPatternsAction.
	 * 
	 * @param jobRegexp
	 * @throws EmptyRegexpException 
	 * @throws IOException 
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer, String jobRegexp) throws IOException, EmptyRegexpException {
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
	 */
	public CopyJobsReplacingPatternsAction(JenkinsServer jenkinsServer, String jobRegexp, List<Pattern> patterns) throws IOException, EmptyRegexpException {
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
	public void run() throws NoJobsToCopyException, IOException, JobNotFoundException, NoPatternsException {
		check();
		
		for (Iterator<Job> it = jobsToCopy.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			System.out.println("Copying \"" + job.getName() + "... ");
			String newJobName;
			try {
				newJobName = jobManager.copyJobReplacingPatterns(job, patterns, true);
				System.out.println("\tDone : \"" + newJobName + "\"");
			} catch (JobAlreadyExistsException e) {
				System.out.println(e);;
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
	public String preview() throws IOException, JobNotFoundException, NoJobsToCopyException, NoPatternsException {
		check();
		
		String preview = "The following jobs will be copied :\n";
		int higherJobNameSize = getHigherJobNameSize();
		
		for (Iterator<Job> it = jobsToCopy.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			String newJobName = null;
			try {
				newJobName = jobManager.copyJobReplacingPatterns(job, patterns, false);
			} catch (JobAlreadyExistsException e) {
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
	 * @throws IOException 
	 */
	public void setJobsRegexp(String jobsRegexp) throws IOException, EmptyRegexpException {
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
