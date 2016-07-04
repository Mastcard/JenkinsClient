package test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;

import engine.ConnectionManager;
import exception.JenkinsConnectionFailedException;

/**
 * The Connection Manager test.
 * 
 * @author mastcard
 *
 */
public class ConnectionManagerTest {

	private JenkinsServer jenkinsServer;
	
	@Test
	public void testLogOnJenkins() {
		jenkinsServer = logOn(TestsConstants.SERVER, TestsConstants.USERNAME, TestsConstants.PASSWORD);
		assertNotNull(jenkinsServer);
	}
	
	/* #### Negative tests. #### */
	
	@Test
	public void testLogOnJenkinsWithWrongServer() {
		jenkinsServer = logOn(TestsConstants.WRONG_SERVER, TestsConstants.USERNAME, TestsConstants.PASSWORD);
		assertNull(jenkinsServer);
	}
	
	@Test
	public void testLogOnJenkinsWithWrongUserName() {
		jenkinsServer = logOn(TestsConstants.SERVER, TestsConstants.WRONG_USERNAME, TestsConstants.PASSWORD);
		assertNull(jenkinsServer);
	}
	
	@Test
	public void testLogOnJenkinsWithWrongPassword() {
		jenkinsServer = logOn(TestsConstants.SERVER, TestsConstants.USERNAME, TestsConstants.WRONG_PASSWORD);
		assertNull(jenkinsServer);
	}
	
	/*
	 * UTIL.
	 */

	private JenkinsServer logOn(String server, String username, String password) {
		ConnectionManager connectionManager = new ConnectionManager(server, username, password);
		JenkinsServer jenkinsServer = null;
		
		try {
			jenkinsServer = connectionManager.logOnJenkins();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JenkinsConnectionFailedException e) {
			e.printStackTrace();
		}
		
		return jenkinsServer;
	}

}
