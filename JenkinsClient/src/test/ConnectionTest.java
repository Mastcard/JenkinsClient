package test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;

import engine.ConnectionManager;
import exception.JenkinsConnectionFailedException;

/**
 * The Connection test.
 * 
 * @author mastcard
 *
 */
public class ConnectionTest {

	private JenkinsServer jenkinsServer;
	
	private static final String SERVER = "http://localhost:8080";
	private static final String USERNAME = "mastcard";
	private static final String PASSWORD = "password";
	
	private static final String WRONG_SERVER = "http://localhost:8888";
	private static final String WRONG_USERNAME = "username";
	private static final String WRONG_PASSWORD = "Password";
	
	@Test
	public void testLogOnJenkins() {
		jenkinsServer = logOn(SERVER, USERNAME, PASSWORD);
		assertNotNull(jenkinsServer);
	}
	
	/* #### Negative tests. #### */
	
	@Test
	public void testLogOnJenkinsWithWrongServer() {
		jenkinsServer = logOn(WRONG_SERVER, USERNAME, PASSWORD);
		assertNull(jenkinsServer);
	}
	
	@Test
	public void testLogOnJenkinsWithWrongUserName() {
		jenkinsServer = logOn(SERVER, WRONG_USERNAME, PASSWORD);
		assertNull(jenkinsServer);
	}
	
	@Test
	public void testLogOnJenkinsWithWrongPassword() {
		jenkinsServer = logOn(SERVER, USERNAME, WRONG_PASSWORD);
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
