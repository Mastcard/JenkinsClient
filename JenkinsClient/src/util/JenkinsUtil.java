package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.View;

/**
 * The class JenkinsUtil.
 * 
 * @author mastcard
 *
 */
public class JenkinsUtil {

	/**
	 * Gets jobs.
	 * 
	 * @param jenkinsServer
	 * @return the jobs
	 * @throws IOException 
	 */
	public static Map<String, Job> getJobs(JenkinsServer jenkinsServer) throws IOException {
		Map<String, Job> jobsMap = jenkinsServer.getJobs();
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
	 * @throws IOException
	 */
	public static Map<String, Job> getJobs(JenkinsServer jenkinsServer, String view) throws IOException, IllegalArgumentException {
		Map<String, Job> jobsMap = jenkinsServer.getJobs(view);
		Map<String, Job> result = new HashMap<String, Job>();
		result.putAll(jobsMap);
		return result;
	}
	
	/**
	 * Gets views.
	 * 
	 * @param jenkinsServer
	 * @return the views
	 * @throws IOException
	 */
	public static Map<String, View> getViews(JenkinsServer jenkinsServer) throws IOException {
		Map<String, View> viewsMap = jenkinsServer.getViews();
		Map<String, View> result = new HashMap<String, View>();
		result.putAll(viewsMap);
		return result;
	}
	
	
}
