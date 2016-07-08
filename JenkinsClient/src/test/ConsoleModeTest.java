package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import model.Action;
import model.Pattern;
import util.Constants;
import console.ConsoleModeMain;
import console.CopyJobsReplacingPatternsAction;
import engine.ConnectionManager;
import engine.JobManager;
import exception.CantRetrieveJobsException;
import exception.CantRetrieveViewsException;
import exception.EmptyRegexpException;
import exception.JenkinsConnectionFailedException;
import exception.JobCreationFailedException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoJobsToRemoveException;
import exception.NoPatternsException;
import exception.NullServerJenkinsException;
import exception.NullServerNameException;
import exception.SeveralJobsWithSameNameInViewException;
import exception.UnknownActionException;
import exception.WrongCommandException;
import exception.WrongURISyntaxException;

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
		String args = "-server " + TestsConstants.SERVER + " -username "
				+ TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " "
				+ Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp
				+ " " + TestsConstants.BEFORE_PATTERN1 + "/"
				+ TestsConstants.AFTER_PATTERN1 + ","
				+ TestsConstants.BEFORE_PATTERN2 + "/"
				+ TestsConstants.AFTER_PATTERN2;

		assertCJRPCommand(args.split(" "), jobsRegexp, false);
	}

	/**
	 * Tests Copy Jobs Replacing Patterns with force.
	 */
	@Test
	public void testCJRPCommandWithForce() {
		String jobsRegexp = ".*trunk-dev.*";
		String args = "-server " + TestsConstants.SERVER + " -username "
				+ TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " "
				+ Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp
				+ " " + TestsConstants.BEFORE_PATTERN1 + "/"
				+ TestsConstants.AFTER_PATTERN1 + ","
				+ TestsConstants.BEFORE_PATTERN2 + "/"
				+ TestsConstants.AFTER_PATTERN2 + " -f";

		assertCJRPCommand(args.split(" "), jobsRegexp, true);
	}

	/**
	 * Tests execute Copy Jobs Replacing Patterns command without force.
	 */
	@Test
	public void testExecuteCJRPCommand() {
		JenkinsServer jenkinsServer = null;
		String jobsRegexp = ".*trunk-dev.*";
		String args = "-server " + TestsConstants.SERVER + " -username "
				+ TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " "
				+ Constants.COPY_JOBS_REPLACING_PATTERNS_ARG + " " + jobsRegexp
				+ " " + TestsConstants.BEFORE_PATTERN1 + "/"
				+ TestsConstants.AFTER_PATTERN1 + ","
				+ TestsConstants.BEFORE_PATTERN2 + "/"
				+ TestsConstants.AFTER_PATTERN2 + " -f";

		// Check command
		// Allow us to retrieve the jenkins server
		// and retrieve jobs map before any action
		Map<String, Job> jobs = null;
		try {
			consoleModeMain.checkArgs(args.split(" "));
			jenkinsServer = consoleModeMain.getJenkinsServer();
			jobManager = new JobManager(jenkinsServer);
			jobs = jobManager.getJobs();
		} catch (UnknownActionException | EmptyRegexpException
				| JenkinsConnectionFailedException | NullServerNameException
				| NullServerJenkinsException | CantRetrieveJobsException
				| SeveralJobsWithSameNameInViewException
				| CantRetrieveViewsException | WrongURISyntaxException e) {
			e.printStackTrace();
		}
		assertEquals(5, jobs.size());

		try {
			jobs = jobManager.filterJobsWithRegexp(jobs, jobsRegexp);
		} catch (EmptyRegexpException e1) {
			e1.printStackTrace();
		}
		assertEquals(2, jobs.size());

		// Execute command
		try {
			consoleModeMain.executeCommand(args.split(" "));
		} catch (WrongCommandException | UnknownActionException
				| EmptyRegexpException | JenkinsConnectionFailedException
				| JobNotFoundException | NoJobsToCopyException
				| NoPatternsException | NullServerNameException
				| NoJobsToRemoveException | NullServerJenkinsException
				| SeveralJobsWithSameNameInViewException
				| CantRetrieveJobsException | CantRetrieveViewsException
				| WrongURISyntaxException e) {
			e.printStackTrace();
		}

		try {
			jobs = jobManager.getJobs();
		} catch (CantRetrieveJobsException e1) {
			e1.printStackTrace();
		}
		assertEquals(7, jobs.size());

		// Asserts new jobs
		String copiedJob1 = "(win64_x64) " + TestsConstants.AFTER_PATTERN1
				+ " Fetch+Compile";
		String copiedJob2 = "(tests) (win64_x64) "
				+ TestsConstants.AFTER_PATTERN1 + " unit-tests";

		Job job1 = null;
		Job job2 = null;

		try {
			job1 = jobManager.getJob(copiedJob1);
			job2 = jobManager.getJob(copiedJob2);
		} catch (JobNotFoundException e) {
			e.printStackTrace();
		}
		assertNotNull(job1);
		assertNotNull(job2);
		assertEquals(copiedJob1, job1.getName());
		assertEquals(copiedJob2, job2.getName());

		try {
			jobManager.deleteJob(copiedJob1);
			jobManager.deleteJob(copiedJob2);
		} catch (JobNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests execute Remove Job command.
	 */
	@Test
	public void testExecuteRJCommand() {
		Map<String, Job> jobs = null;
		ConnectionManager connectionManager = new ConnectionManager(
				TestsConstants.SERVER, TestsConstants.USERNAME,
				TestsConstants.PASSWORD);
		JenkinsServer jenkinsServer = null;

		try {
			jenkinsServer = connectionManager.logOnJenkins();
		} catch (JenkinsConnectionFailedException | NullServerNameException
				| WrongURISyntaxException e) {
			e.printStackTrace();
		}
		try {
			jobManager = new JobManager(jenkinsServer);
		} catch (NullServerJenkinsException e1) {
			e1.printStackTrace();
		}
		try {
			jobs = jobManager.getJobs();
		} catch (CantRetrieveJobsException e) {
			e.printStackTrace();
		}
		assertNotNull(jobs);
		assertFalse(jobs.isEmpty());

		int initialSize = jobs.size();
		List<Pattern> patterns1 = new ArrayList<Pattern>();
		List<Pattern> patterns2 = new ArrayList<Pattern>();
		patterns1.add(new Pattern("trunk-dev", "4.2-SP07_Patch_COR"));
		patterns2.add(new Pattern("trunk-dev", "4.2-SP08_Patch_COR"));

		try {
			jobManager.copyJobReplacingPatterns(
					"(win64_x64) trunk-dev Fetch+Compile", patterns1, true);
			jobManager.copyJobReplacingPatterns(
					"(win64_x64) trunk-dev Fetch+Compile", patterns2, true);
		} catch (JobCreationFailedException | JobNotFoundException e) {
			e.printStackTrace();
		}

		try {
			jobs = jobManager.getJobs();
		} catch (CantRetrieveJobsException e) {
			e.printStackTrace();
		}

		assertEquals(initialSize + 2, jobs.size());
		try {
			assertNotNull(jobManager.getJob("(win64_x64) 4.2-SP07_Patch_COR Fetch+Compile"));
			assertNotNull(jobManager.getJob("(win64_x64) 4.2-SP08_Patch_COR Fetch+Compile"));
		} catch (JobNotFoundException e) {
			e.printStackTrace();
		}

		String args = "-server " + TestsConstants.SERVER + " -username "
				+ TestsConstants.USERNAME + " -password "
				+ TestsConstants.PASSWORD + " " + Constants.REMOVE_JOBS_ARG + " .*4.2.* -f";
		try {
			consoleModeMain.executeCommand(args.split(" "));
		} catch (SeveralJobsWithSameNameInViewException
				| UnknownActionException | EmptyRegexpException
				| JenkinsConnectionFailedException | NullServerNameException
				| NullServerJenkinsException | CantRetrieveJobsException
				| CantRetrieveViewsException | WrongURISyntaxException
				| WrongCommandException | JobNotFoundException
				| NoJobsToCopyException | NoPatternsException
				| NoJobsToRemoveException e) {
			e.printStackTrace();
		}

		try {
			jobs = jobManager.getJobs();
		} catch (CantRetrieveJobsException e) {
			e.printStackTrace();
		}
		
		assertEquals(initialSize, jobs.size());
		try {
			assertNull(jobManager.getJob("(win64_x64) 4.2-SP07_Patch_COR Fetch+Compile"));
			assertNull(jobManager.getJob("(win64_x64) 4.2-SP08_Patch_COR Fetch+Compile"));
		} catch (JobNotFoundException e) {
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
	private void assertCJRPCommand(String[] args, String jobsRegexp,
			boolean forceEnabled) {
		consoleModeMain = new ConsoleModeMain();

		try {
			consoleModeMain.checkArgs(args);
		} catch (UnknownActionException | EmptyRegexpException
				| JenkinsConnectionFailedException | NullServerNameException
				| NullServerJenkinsException
				| SeveralJobsWithSameNameInViewException
				| CantRetrieveJobsException | CantRetrieveViewsException
				| WrongURISyntaxException e) {
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

		CopyJobsReplacingPatternsAction cjrpAction = (CopyJobsReplacingPatternsAction) consoleModeMain
				.getAction();
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
