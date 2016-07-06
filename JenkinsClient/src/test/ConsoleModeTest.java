package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import model.Action;
import model.Pattern;
import util.Constants;
import console.ConsoleModeMain;
import console.CopyJobsReplacingPatternsAction;
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
	private static  ConsoleModeMain consoleModeMain;

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
	
	private void assertCJRPCommand(String[] args, String jobsRegexp, boolean forceEnabled) {
		consoleModeMain = new ConsoleModeMain();
		
		try {
			consoleModeMain.executeCommand(args);
		} catch (WrongCommandException | UnknownActionException e) {
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
