package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import engine.ConnectionManager;
import engine.JobManager;
import exception.JenkinsConnectionFailedException;
import exception.SeveralJobsWithSameNameInViewException;

/**
 * The Class JobManagerTest.
 * 
 * @author mastcard
 *
 */
public class JobManagerTest {

	private static JenkinsServer jenkinsServer;
	private static JobManager jobManager;
	
	/**
	 * Set up.
	 */
	@BeforeClass
	public static void setUp() {
		ConnectionManager connectionManager = new ConnectionManager(TestsConstants.SERVER, TestsConstants.USERNAME, TestsConstants.PASSWORD);
		
		try {
			jenkinsServer = connectionManager.logOnJenkins();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JenkinsConnectionFailedException e) {
			e.printStackTrace();
		}
		
		jobManager = new JobManager(jenkinsServer);
	}
	
	@Test
	public void testGetUnusedJobs() {
		Map<String, Job> jobs = null;
		
		try {
			jobs = jobManager.getUnusedJobs();
		} catch (SeveralJobsWithSameNameInViewException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		assertNotNull(jobs);
		assertFalse(jobs.isEmpty());
		assertEquals(1, jobs.size());
		assertFalse(!jobs.containsKey("unused job"));
	}
	
}
