package engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Pattern;
import util.Constants;
import util.JenkinsUtil;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.View;

import exception.EmptyRegexpException;
import exception.JobNotFoundException;
import exception.SeveralJobsWithSameNameInViewException;

/**
 * The Class JobManager.
 * 
 * @author mastcard
 *
 */
public class JobManager {

	/** The jenkins server. */
	private JenkinsServer jenkinsServer;
	
	/**
	 * Instantiates a new JobManager
	 * 
	 * @param jenkinsServer
	 */
	public JobManager(JenkinsServer jenkinsServer) {
		this.jenkinsServer = jenkinsServer;
	}
	
	/**
	 * Gets the unused jobs.
	 * Returns the jobs which are not used in any view of the jenkins server.
	 * 
	 * @return the unused jobs
	 * @throws IllegalArgumentException 
	 * @throws IOException
	 */
	public Map<String, Job> getUnusedJobs() throws SeveralJobsWithSameNameInViewException, IOException {
		Map<String, Job> jobsMap = JenkinsUtil.getJobs(jenkinsServer);
		Map<String, View> viewsMap = JenkinsUtil.getViews(jenkinsServer);
		
		// Removes the "All" view from the view map
		if (viewsMap.containsKey(Constants.ALL_JOBS_VIEW_NAME)) {
			viewsMap.remove(Constants.ALL_JOBS_VIEW_NAME);
		}
		
		// Copies the jobs HashMap to not throw a ConcurrentModificationException.
		Map<String, Job> unusedJobs = new HashMap<String, Job>();
		unusedJobs.putAll(jobsMap);
		
		for (Iterator<View> itView = viewsMap.values().iterator(); itView.hasNext(); ) {
			View view = itView.next();
			Map<String, Job> viewJobs = null;
			
			try {
				viewJobs = JenkinsUtil.getJobs(jenkinsServer, view.getName());
			} catch (IllegalArgumentException e) {
				throw new SeveralJobsWithSameNameInViewException(view.getName());
			}
				
			for (Iterator<Job> itJob = jobsMap.values().iterator(); itJob.hasNext(); ) {
				Job job = itJob.next();
				if (viewJobs.containsKey(job.getName())) {
					unusedJobs.remove(job.getName());
				}
			}
			
		}
		
		return unusedJobs;
	}
	
	/**
	 * Gets jobs with regexp.
	 * 
	 * @return jobs map
	 * @throws IOException 
	 * @throws EmptyRegexpException 
	 */
	public Map<String, Job> getJobsWithRegexp(String regexp) throws IOException, EmptyRegexpException {
		if (regexp == null || regexp.isEmpty()) {
			throw new EmptyRegexpException();
		}
		
		Map<String, Job> jobs = JenkinsUtil.getJobs(jenkinsServer);
		Map<String, Job> jobsWithRegexp = new HashMap<String, Job>();
		
		for (Iterator<Job> it = jobs.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			if (job.getName().matches(regexp)) {
				jobsWithRegexp.put(job.getName(), job);
			}
		}
		
		return jobsWithRegexp;
	}
	
	/**
	 * Filters jobs with regexp.
	 * 
	 * @param jobs
	 * @param regexp
	 * @return the jobs maps
	 * @throws EmptyRegexpException
	 */
	public Map<String, Job> filterJobsWithRegexp(Map<String, Job> jobs, String regexp) throws EmptyRegexpException {
		if (regexp == null || regexp.isEmpty()) {
			throw new EmptyRegexpException();
		}

		Map<String, Job> jobsWithRegexp = new HashMap<String, Job>();
		
		for (Iterator<Job> it = jobs.values().iterator(); it.hasNext(); ) {
			Job job = it.next();
			if (job.getName().matches(regexp)) {
				jobsWithRegexp.put(job.getName(), job);
			}
		}
		
		return jobsWithRegexp;
	}
	
	/**
	 * Copies job replacing patterns.
	 * 
	 * @param jobName the name of the job to copy
	 * @param patterns the patterns with old and new content
	 * @return the new job name
	 * @throws IOException
	 * @throws JobNotFoundException
	 */
	public String copyJobReplacingPatterns(String jobName, List<Pattern> patterns, boolean execute) throws IOException, JobNotFoundException {
		Job jobToCopy = jenkinsServer.getJob(jobName);
		if (jobToCopy == null) {
			throw new JobNotFoundException(jobName);
		}
		
		String jobToCopyXml = jenkinsServer.getJobXml(jobName);
		String newJobName = jobName;
		String newJobXml = jobToCopyXml;
		
		for (int i = 0; i < patterns.size(); i++) {
			Pattern pattern = patterns.get(i);
			newJobName = newJobName.replaceAll(pattern.getBefore(), pattern.getAfter());
			newJobXml = newJobXml.replaceAll(pattern.getBefore(), pattern.getAfter());
		}
		
		if (execute) {
			jenkinsServer.createJob(newJobName, newJobXml);
		}

		return newJobName;
	}
	
}
