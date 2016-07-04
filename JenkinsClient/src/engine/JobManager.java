package engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.Constants;
import util.JenkinsUtil;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.View;

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
	
}
