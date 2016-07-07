package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import model.Action;
import model.Pattern;
import util.Constants;
import util.JenkinsUtil;
import console.ConsoleModeMain;
import console.CopyJobsReplacingPatternsAction;
import engine.JobManager;
import exception.EmptyRegexpException;
import exception.JenkinsConnectionFailedException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoPatternsException;
import exception.NullServerNameException;
import exception.UnknownActionException;
import exception.WrongCommandException;

/**
 * The class ConsoleModeTest.
 * 
 * @author mastcard
 *
 */
public class ConsoleModeTest {

	/** The console mode main. */
	private static ConsoleModeMain consoleModeMain;
	
	/** The job manager. */
	private static JobManager jobManager;

	/**
	 * Tests Copy Jobs Replacing Patterns without force.
	 */
	@Test
	public void testCJRPCommandWithoutForce() {
		String jobsRegexp = ".*trunk-dev.*";
		String args = "-server " + TestsConstants.SERVER + " -username " + TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " " + Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp + " "
				+ TestsConstants.BEFORE_PATTERN1 + "/" + TestsConstants.AFTER_PATTERN1 + "," + TestsConstants.BEFORE_PATTERN2 
				+ "/" + TestsConstants.AFTER_PATTERN2;
		
		assertCJRPCommand(args.split(" "), jobsRegexp, false);
	}
	
	/**
	 * Tests Copy Jobs Replacing Patterns with force.
	 */
	@Test
	public void testCJRPCommandWithForce() {
		String jobsRegexp = ".*trunk-dev.*";
		String args = "-server " + TestsConstants.SERVER + " -username " + TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " " + Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp + " "
				+ TestsConstants.BEFORE_PATTERN1 + "/" + TestsConstants.AFTER_PATTERN1 + "," + TestsConstants.BEFORE_PATTERN2 
				+ "/" + TestsConstants.AFTER_PATTERN2 + " -f";
		
		assertCJRPCommand(args.split(" "), jobsRegexp, true);
	}
	
	/**
	 * Tests execute Copy Jobs Replacing Patterns command without force.
	 */
	@Test
	public void testExecuteCJRPCommand() {
		JenkinsServer jenkinsServer = null;
		String jobsRegexp = ".*trunk-dev.*";
		String args = "-server " + TestsConstants.SERVER + " -username " + TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " " + Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp + " "
				+ TestsConstants.BEFORE_PATTERN1 + "/" + TestsConstants.AFTER_PATTERN1 + "," + TestsConstants.BEFORE_PATTERN2 
				+ "/" + TestsConstants.AFTER_PATTERN2 + " -f";
		
		// Check command
		// Allow us to retrieve the jenkins server
		// 	and retrieve jobs map before any action
		Map<String, Job> jobs = null;
		try {
			consoleModeMain.checkArgs(args.split(" "));
			jenkinsServer = consoleModeMain.getJenkinsServer();
			jobs = JenkinsUtil.getJobs(jenkinsServer);
		} catch (IOException | UnknownActionException | EmptyRegexpException
				| URISyntaxException | JenkinsConnectionFailedException | NullServerNameException e) {
			e.printStackTrace();
		}
		assertEquals(5, jobs.size());

		jobManager = new JobManager(jenkinsServer);
		try {
			jobs = jobManager.filterJobsWithRegexp(jobs, jobsRegexp);
		} catch (EmptyRegexpException e1) {
			e1.printStackTrace();
		}
		assertEquals(2, jobs.size());

		// Execute command
		try {
			consoleModeMain.executeCommand(args.split(" "));
		} catch (WrongCommandException | UnknownActionException | IOException
				| EmptyRegexpException | URISyntaxException
				| JenkinsConnectionFailedException | JobNotFoundException
				| NoJobsToCopyException | NoPatternsException | NullServerNameException e) {
			e.printStackTrace();
		}
		
		// Assert new jobs map
		try {
			jobs = JenkinsUtil.getJobs(jenkinsServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(7, jobs.size());
		
		// Asserts new jobs
		String copiedJob1 = "(win64_x64) " + TestsConstants.AFTER_PATTERN1 + " Fetch+Compile";
		String copiedJob2 = "(tests) (win64_x64) " + TestsConstants.AFTER_PATTERN1 + " unit-tests";
		
		Job job1 = null;
		Job job2 = null;

		try {
			job1 = jenkinsServer.getJob(copiedJob1);
			job2 = jenkinsServer.getJob(copiedJob2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(job1);
		assertNotNull(job2);
		assertEquals(copiedJob1, job1.getName());
		assertEquals(copiedJob2, job2.getName());

		try {
			jenkinsServer.deleteJob(copiedJob1);
			jenkinsServer.deleteJob(copiedJob2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asserts Copy Jobs Replacing Patterns command.
	 * 
	 * @param args
	 * @param jobsRegexp
	 * @param forceEnabled
	 */
	private void assertCJRPCommand(String[] args, String jobsRegexp, boolean forceEnabled) {
		consoleModeMain = new ConsoleModeMain();
		
		try {
			consoleModeMain.checkArgs(args);
		} catch (UnknownActionException | IOException
				| EmptyRegexpException | URISyntaxException
				| JenkinsConnectionFailedException | NullServerNameException e) {
			e.printStackTrace();
		}
		
		boolean serverIsCorrect = consoleModeMain.isServerIsCorrect();
		boolean usernameIsCorrect = consoleModeMain.isUsernameIsCorrect();
		boolean passwordIsCorrect = consoleModeMain.isPasswordIsCorrect();
		boolean actionIsCorrect = consoleModeMain.isActionIsCorrect();
		boolean forceIsEnabled = consoleModeMain.isForceIsEnabled();
		
		assertEquals(true, serverIsCorrect);
		assertEquals(true, usernameIsCorrect);
		assertEquals(true, passwordIsCorrect);
		assertEquals(true, actionIsCorrect);
		assertEquals(forceEnabled, forceIsEnabled);
		
		Action action = consoleModeMain.getAction();
		assertEquals(true, action instanceof CopyJobsReplacingPatternsAction);
		
		CopyJobsReplacingPatternsAction cjrpAction = (CopyJobsReplacingPatternsAction) consoleModeMain.getAction();
		assertEquals(TestsConstants.SERVER, cjrpAction.getServer());
		assertEquals(TestsConstants.USERNAME, cjrpAction.getUsername());
		assertEquals(TestsConstants.PASSWORD, cjrpAction.getPassword());
		assertEquals(jobsRegexp, cjrpAction.getJobsRegexp());
		assertEquals(forceEnabled, cjrpAction.isExecute());
		
		List<Pattern> patterns = cjrpAction.getPatterns();
		
		Pattern pattern1 = patterns.get(0);
		assertEquals(TestsConstants.BEFORE_PATTERN1, pattern1.getBefore());
		assertEquals(TestsConstants.AFTER_PATTERN1, pattern1.getAfter());
		
		Pattern pattern2 = patterns.get(1);
		assertEquals(TestsConstants.BEFORE_PATTERN2, pattern2.getBefore());
		assertEquals(TestsConstants.AFTER_PATTERN2, pattern2.getAfter());
	}
	
}
