package test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;

import engine.ConnectionManager;
import exception.JenkinsNotRunningException;

/**
 * The Connection test.
 * 
 * @author mastcard
 *
 */
public class ConnectionTest {

	private static final String server = "http://localhost:8080";
	private static final String username = "mastcard";
	private static final String password = "password";
	
	@Test
	public void testLogOnJenkins() throws URISyntaxException, JenkinsNotRunningException {
		ConnectionManager connectionManager = new ConnectionManager(server, username, password);
		JenkinsServer jenkinsserver = connectionManager.logOnJenkins();
		assertNotNull(jenkinsserver);
	}

}
