package engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Pattern;
import util.Constants;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.View;

import exception.CantRetrieveJobsException;
import exception.CantRetrieveViewsException;
import exception.EmptyRegexpException;
import exception.JobCreationFailedException;
import exception.JobNotFoundException;
import exception.NullServerJenkinsException;
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

	/** The view manager. */
	private ViewManager viewManager;

	/**
	 * Instantiates a new JobManager
	 * 
	 * @param jenkinsServer
	 * @throws NullServerJenkinsException
	 */
	public JobManager(JenkinsServer jenkinsServer)
			throws NullServerJenkinsException {
		if (jenkinsServer == null) {
			throw new NullServerJenkinsException();
		}

		this.jenkinsServer = jenkinsServer;
		viewManager = new ViewManager(jenkinsServer);
	}

	/**
	 * Gets the unused jobs. Returns the jobs which are not used in any view of
	 * the jenkins server.
	 * 
	 * @return the unused jobs
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws CantRetrieveJobsException
	 * @throws CantRetrieveViewsException
	 */
	public Map<String, Job> getUnusedJobs()
			throws SeveralJobsWithSameNameInViewException,
			CantRetrieveJobsException, CantRetrieveViewsException {
		
		Map<String, Job> jobsMap = getJobs();
		Map<String, View> viewsMap = viewManager.getViews();

		// Removes the "All" view from the view map
		if (viewsMap.containsKey(Constants.ALL_JOBS_VIEW_NAME)) {
			viewsMap.remove(Constants.ALL_JOBS_VIEW_NAME);
		}

		// Copies the jobs HashMap to not throw a
		// ConcurrentModificationException.
		Map<String, Job> unusedJobs = new HashMap<String, Job>();
		unusedJobs.putAll(jobsMap);

		for (Iterator<View> itView = viewsMap.values().iterator(); itView
				.hasNext();) {
			View view = itView.next();
			Map<String, Job> viewJobs = null;

			try {
				viewJobs = getJobs(view.getName());
			} catch (IllegalArgumentException e) {
				throw new SeveralJobsWithSameNameInViewException(view.getName());
			}

			for (Iterator<Job> itJob = jobsMap.values().iterator(); itJob
					.hasNext();) {
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
	 * @throws CantRetrieveJobsException
	 */
	public Map<String, Job> getJobsWithRegexp(String regexp)
			throws EmptyRegexpException, CantRetrieveJobsException {
		if (regexp == null || regexp.isEmpty()) {
			throw new EmptyRegexpException();
		}

		Map<String, Job> jobs = getJobs();
		Map<String, Job> jobsWithRegexp = new HashMap<String, Job>();

		for (Iterator<Job> it = jobs.values().iterator(); it.hasNext();) {
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
	public Map<String, Job> filterJobsWithRegexp(Map<String, Job> jobs,
			String regexp) throws EmptyRegexpException {
		if (regexp == null || regexp.isEmpty()) {
			throw new EmptyRegexpException();
		}

		Map<String, Job> jobsWithRegexp = new HashMap<String, Job>();

		for (Iterator<Job> it = jobs.values().iterator(); it.hasNext();) {
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
	 * @param jobName
	 *            the name of the job to copy
	 * @param patterns
	 *            the patterns with old and new content
	 * @return the new job name
	 * @throws IOException
	 * @throws JobNotFoundException
	 * @throws JobAlreadyExistsException
	 */
	public String copyJobReplacingPatterns(String jobName,
			List<Pattern> patterns, boolean execute)
			throws JobNotFoundException, JobCreationFailedException {

		Job jobToCopy = getJob(jobName);
		if (jobToCopy == null) {
			throw new JobNotFoundException(jobName);
		}

		String jobToCopyXml = getJobXml(jobName);
		String newJobName = jobName;
		String newJobXml = jobToCopyXml;

		for (int i = 0; i < patterns.size(); i++) {
			Pattern pattern = patterns.get(i);
			newJobName = newJobName.replaceAll(pattern.getBefore(),
					pattern.getAfter());
			newJobXml = newJobXml.replaceAll(pattern.getBefore(),
					pattern.getAfter());
		}

		if (execute) {
			createJob(newJobName, newJobXml);
		}

		return newJobName;
	}

	/**
	 * Copies job replacing patterns.
	 * 
	 * @param job
	 *            the job to copy
	 * @param patterns
	 *            the patterns with old and new content
	 * @return the new job name
	 * @throws JobNotFoundException
	 * @throws JobAlreadyExistsException
	 */
	public String copyJobReplacingPatterns(Job job, List<Pattern> patterns,
			boolean execute) throws JobNotFoundException, JobCreationFailedException {

		if (job == null) {
			throw new JobNotFoundException("");
		}

		String jobToCopyXml = getJobXml(job.getName());
		String newJobName = job.getName();
		String newJobXml = jobToCopyXml;

		for (int i = 0; i < patterns.size(); i++) {
			Pattern pattern = patterns.get(i);
			newJobName = newJobName.replaceAll(pattern.getBefore(),
					pattern.getAfter());
			newJobXml = newJobXml.replaceAll(pattern.getBefore(),
					pattern.getAfter());
		}

		if (execute) {
			createJob(newJobName, newJobXml);
		}

		return newJobName;
	}

	/**
	 * Gets job.
	 * 
	 * @param name
	 * @return the job
	 * @throws JobNotFoundException
	 */
	public Job getJob(String jobName) throws JobNotFoundException {
		Job job = null;

		try {
			job = jenkinsServer.getJob(jobName);
		} catch (IOException e) {
			throw new JobNotFoundException(jobName);
		}

		return job;
	}

	/**
	 * Gets the job xml.
	 * 
	 * @param jobName
	 * @return the job xml
	 * @throws JobNotFoundException
	 */
	public String getJobXml(String jobName) throws JobNotFoundException {
		String jobXml = null;

		try {
			jobXml = jenkinsServer.getJobXml(jobName);
		} catch (IOException e) {
			throw new JobNotFoundException(jobName);
		}

		return jobXml;
	}

	/**
	 * Create job
	 * 
	 * @param jobName
	 * @param jobXml
	 * @throws JobAlreadyExistsException
	 */
	public void createJob(String jobName, String jobXml)
			throws JobCreationFailedException {
		try {
			jenkinsServer.createJob(jobName, jobXml, false);
		} catch (IOException e) {
			throw new JobCreationFailedException(jobName);
		}
	}

	/**
	 * Deletes job.
	 * 
	 * @param jobName
	 * @throws IOException
	 */
	public void deleteJob(String jobName) throws JobNotFoundException {
		try {
			jenkinsServer.deleteJob(jobName, false);
		} catch (IOException e) {
			throw new JobNotFoundException(jobName);
		}
	}

	/**
	 * Gets jobs.
	 * 
	 * @param jenkinsServer
	 * @return the jobs
	 * @throws CantRetrieveJobsException
	 * @throws IOException
	 */
	public Map<String, Job> getJobs() throws CantRetrieveJobsException {
		Map<String, Job> jobsMap = null;

		try {
			jobsMap = jenkinsServer.getJobs();
		} catch (IOException e) {
			throw new CantRetrieveJobsException();
		}

		Map<String, Job> result = new HashMap<String, Job>();
		result.putAll(jobsMap);
		return result;
	}

	/**
	 * Gets jobs in given view.
	 * 
	 * @param jenkinsServer
	 * @param view
	 * @return the jobs in the given view
	 * @throws CantRetrieveJobsException
	 * @throws IOException
	 */
	public Map<String, Job> getJobs(String view)
			throws CantRetrieveJobsException {
		Map<String, Job> jobsMap = null;

		try {
			jobsMap = jenkinsServer.getJobs(view);
		} catch (IOException e) {
			throw new CantRetrieveJobsException();
		}

		Map<String, Job> result = new HashMap<String, Job>();
		result.putAll(jobsMap);
		return result;
	}

}
