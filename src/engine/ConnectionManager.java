package engine;

import java.net.URI;
import java.net.URISyntaxException;

import com.offbytwo.jenkins.JenkinsServer;

import exception.JenkinsConnectionFailedException;
import exception.NullServerNameException;
import exception.WrongURISyntaxException;

/**
 * The class Connection.
 * 
 * @author mastcard
 *
 */
public class ConnectionManager {

	/** The server. */
	private String server;
	
	/** The user name. */
	private String username;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new ConnectionManager.
	 * 
	 * @param server
	 * @param username
	 * @param password
	 */
	public ConnectionManager(String server, String username, String password) {
		this.server = server;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Logs on.
	 * 
	 * @param server
	 * @param user
	 * @param password
	 * @return
	 * @throws URISyntaxException
	 * @throws NullServerNameException 
	 * @throws WrongURISyntaxException 
	 * @throws JenkinsNotRunningException 
	 */
	public JenkinsServer logOnJenkins() throws JenkinsConnectionFailedException, NullServerNameException, WrongURISyntaxException {
		if (server == null) {
			throw new NullServerNameException();
		}
		
		URI uri;
		try {
			uri = new URI(server);
		} catch (URISyntaxException e) {
			throw new WrongURISyntaxException(server);
		}
		JenkinsServer jenkinsServer = new JenkinsServer(uri, username, password);
		
		if (jenkinsServer != null) {
			if (jenkinsServer.isRunning()) {
				return jenkinsServer;
			}
		}
		
		throw new JenkinsConnectionFailedException(server);
	}
	
}
