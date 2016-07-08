package model;

import java.io.IOException;

import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoJobsToRemoveException;
import exception.NoPatternsException;

/**
 * The class Action.
 * 
 * @author mastcard
 *
 */
public abstract class Action {
	
	/** The server. */
	private String server;
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The boolean execute. */
	private boolean execute;
	
	/**
	 * Instantiates a new Action.
	 * 
	 * @param args
	 */
	public Action() {
		execute = false;
	}
	
	/**
	 * Execute.
	 * @throws NoJobsToCopyException 
	 * @throws JobNotFoundException 
	 * @throws IOException 
	 * @throws NoPatternsException 
	 * @throws NoJobsToRemoveException 
	 */
	public abstract void run() throws NoJobsToCopyException, JobNotFoundException, NoPatternsException, NoJobsToRemoveException;
	
	/**
	 * Preview.
	 * 
	 * @return preview of the result
	 * @throws JobNotFoundException 
	 * @throws IOException 
	 * @throws NoJobsToCopyException 
	 * @throws NoPatternsException 
	 * @throws NoJobsToRemoveException 
	 */
	public abstract String preview() throws JobNotFoundException, NoJobsToCopyException, NoPatternsException, NoJobsToRemoveException;
	
	/**
	 * Is execution enabled.
	 * 
	 * @return execute
	 */
	public boolean isExecute() {
		return execute;
	}

	/**
	 * Sets the boolean execute.
	 * 
	 * @param execute
	 */
	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	/**
	 * Gets the server.
	 * 
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Sets the server.
	 * 
	 * @param server
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}