package console;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.Constants;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import engine.JobManager;
import exception.CantRetrieveJobsException;
import exception.CantRetrieveViewsException;
import exception.EmptyRegexpException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoJobsToRemoveException;
import exception.NoPatternsException;
import exception.NullServerJenkinsException;
import exception.SeveralJobsWithSameNameInViewException;
import model.Action;

/**
 * The class RemoveJobsAction.
 * 
 * @author mastcard
 *
 */
public class RemoveJobsAction extends Action {

	/** The jobs regexp. */
	private String jobsRegexp;

	/** The job manager. */
	private JobManager jobManager;

	/** The jobs to remove. */
	private Map<String, Job> jobsToRemove = new HashMap<String, Job>();

	/**
	 * Instantiates a new RemoveJobsAction.
	 * 
	 * @param jenkinsServer
	 * @throws NullServerJenkinsException
	 */
	public RemoveJobsAction(JenkinsServer jenkinsServer)
			throws NullServerJenkinsException {
		super();
		jobManager = new JobManager(jenkinsServer);
	}

	/**
	 * Instantiates a new RemoveJobsAction.
	 * 
	 * @param jenkinsServer
	 * @param jobsRegexp
	 * @throws EmptyRegexpException
	 * @throws IOException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws CantRetrieveViewsException
	 */
	public RemoveJobsAction(JenkinsServer jenkinsServer, String jobsRegexp)
			throws EmptyRegexpException, NullServerJenkinsException,
			SeveralJobsWithSameNameInViewException, CantRetrieveJobsException,
			CantRetrieveViewsException {

		this(jenkinsServer);
		this.jobsRegexp = jobsRegexp;
		updateJobsToRemove();
	}

	@Override
	public void run() throws NoJobsToCopyException, JobNotFoundException,
			NoPatternsException, NoJobsToRemoveException {
		check();

		for (Iterator<Job> it = jobsToRemove.values().iterator(); it.hasNext();) {
			Job job = it.next();
			String jobName = job.getName();
			System.out.print("\tRemoving " + jobName + "... ");
			jobManager.deleteJob(jobName);
			System.out.println("Done");
		}
	}

	@Override
	public String preview() throws JobNotFoundException, NoJobsToCopyException,
			NoPatternsException, NoJobsToRemoveException {
		check();

		String preview = Constants.RJ_RESULT_PREVIEW_MESSAGE;

		for (Iterator<Job> it = jobsToRemove.values().iterator(); it.hasNext();) {
			Job job = it.next();
			preview += "\t" + job.getName() + "\n";
		}

		return preview;
	}

	/**
	 * Check.
	 * 
	 * @throws NoJobsToRemoveException
	 */
	private void check() throws NoJobsToRemoveException {
		if (jobsToRemove.isEmpty()) {
			throw new NoJobsToRemoveException();
		}
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
	 * @throws IOException
	 * @throws EmptyRegexpException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws CantRetrieveViewsException
	 */
	public void setJobsRegexp(String jobsRegexp) throws EmptyRegexpException,
			SeveralJobsWithSameNameInViewException, CantRetrieveJobsException,
			CantRetrieveViewsException {

		this.jobsRegexp = jobsRegexp;
		updateJobsToRemove();
	}

	/**
	 * Updates jobs to remove.
	 * 
	 * @throws IOException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws EmptyRegexpException
	 * @throws CantRetrieveJobsException
	 * @throws CantRetrieveViewsException
	 */
	private void updateJobsToRemove()
			throws SeveralJobsWithSameNameInViewException,
			EmptyRegexpException, CantRetrieveJobsException,
			CantRetrieveViewsException {

		if (jobsRegexp != null) {
			if (jobsRegexp.equalsIgnoreCase(Constants.ALL_UNUSED_JOBS_OPTION)) {
				jobsToRemove = jobManager.getUnusedJobs();
			} else {
				jobsToRemove = jobManager.getJobsWithRegexp(jobsRegexp);
			}
		}
	}

}
