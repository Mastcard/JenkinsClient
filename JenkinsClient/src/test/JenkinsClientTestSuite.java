package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite.
 * 
 * @author mastcard
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	ConnectionManagerTest.class,
	JobManagerTest.class
})

public class JenkinsClientTestSuite {
}
