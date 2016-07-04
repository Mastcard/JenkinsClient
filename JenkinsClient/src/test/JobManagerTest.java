package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import engine.ConnectionManager;
import engine.JobManager;
import exception.JenkinsConnectionFailedException;
import exception.JobNotFoundException;
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
	
	/**
	 * Tests get unused jobs.
	 */
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
		assertFalse(!jobs.containsKey(TestsConstants.UNUSED_JOB_NAME));
	}
	
	/**
	 * Tests copy job replacing patterns.
	 */
	@Test
	public void testCopyJobReplacingPatterns() {
		
		//
		// Prepares patterns.
		//
		List<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(new Pattern(TestsConstants.BRANCH_PATTERN_BEFORE, TestsConstants.BRANCH_PATTERN_AFTER));
		assertFalse(patterns.isEmpty());
		
		//
		// Copies job replacing patterns.
		//
		String newJobName = null;
		try {
			newJobName = jobManager.copyJobReplacingPatterns(TestsConstants.JOB_TO_COPY_NAME, patterns);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JobNotFoundException e) {
			e.printStackTrace();
		}
		assertNotNull(newJobName);
		assertEquals(TestsConstants.COPIED_JOB_NAME, newJobName);
		
		Job newJob = null;
		try {
			newJob = jenkinsServer.getJob(newJobName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(newJob);
		
		//
		// Deletes the copied job
		//
		if (newJob != null) {
			try {
				jenkinsServer.deleteJob(newJobName);
				assertNull(jenkinsServer.getJob(newJobName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
