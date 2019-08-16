package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import com.offbytwo.jenkins.JenkinsServer;

import model.Action;
import engine.ActionHelpers;
import engine.ConnectionManager;
import exception.CantRetrieveJobsException;
import exception.CantRetrieveViewsException;
import exception.EmptyRegexpException;
import exception.JenkinsConnectionFailedException;
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
import util.Constants;

/**
 * The Console mode main class.
 * 
 * @author mastcard
 *
 */
public class ConsoleModeMain {

	/** The connection manager. */
	private ConnectionManager connectionManager;

	/** The jenkins server. */
	private JenkinsServer jenkinsServer;

	/** The server. */
	private String server;

	/** The user name. */
	private String username;

	/** The password. */
	private String password;

	/** The action. */
	private Action action;

	/** The boolean server is correct. */
	private boolean serverIsCorrect;

	/** The boolean user name is correct. */
	private boolean usernameIsCorrect;

	/** The boolean password is correct. */
	private boolean passwordIsCorrect;

	/** The boolean action is correct. */
	private boolean actionIsCorrect;

	/** The boolean force is enabled. */
	private boolean forceIsEnabled;

	/**
	 * Instantiates a new ConsoleModeMain.
	 */
	public ConsoleModeMain() {
	}

	/**
	 * Executes command.
	 * 
	 * @param args
	 * @throws WrongCommandException
	 * @throws UnknownActionException
	 * @throws EmptyRegexpException
	 * @throws JenkinsConnectionFailedException
	 * @throws NoPatternsException
	 * @throws NoJobsToCopyException
	 * @throws JobNotFoundException
	 * @throws NullServerNameException
	 * @throws NoJobsToRemoveException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveViewsException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws WrongURISyntaxException 
	 */
	public void executeCommand(String[] args) throws WrongCommandException,
			UnknownActionException, EmptyRegexpException, JobNotFoundException,
			NoJobsToCopyException, NoPatternsException,
			NullServerNameException, NoJobsToRemoveException,
			NullServerJenkinsException, SeveralJobsWithSameNameInViewException,
			JenkinsConnectionFailedException, CantRetrieveJobsException,
			CantRetrieveViewsException, WrongURISyntaxException {

		// Check args
		if (!checkArgs(args)) {
			throw new WrongCommandException();
		}

		// Preview
		boolean confirm;
		if (!forceIsEnabled) {
			String preview = action.preview();
			System.out.println(preview);
			confirm = doesUserConfirm();
		} else {
			confirm = true;
		}

		// Run action
		if (confirm) {
			System.out.println(Constants.RUN_ACTION_MESSAGE);
			action.run();
			System.out.println(Constants.ACTION_COMPLETED_MESSAGE);
		}
	}

	/**
	 * Check args.
	 * 
	 * @param args
	 * @return true if command is correct, false otherwise
	 * @throws UnknownActionException
	 * @throws EmptyRegexpException
	 * @throws IOException
	 * @throws JenkinsConnectionFailedException
	 * @throws URISyntaxException
	 * @throws NullServerNameException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveViewsException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws WrongURISyntaxException 
	 */
	public boolean checkArgs(String[] args) throws UnknownActionException, EmptyRegexpException,
			JenkinsConnectionFailedException, NullServerNameException,
			NullServerJenkinsException, SeveralJobsWithSameNameInViewException,
			CantRetrieveJobsException, CantRetrieveViewsException, WrongURISyntaxException {

		serverIsCorrect = true;
		usernameIsCorrect = true;
		passwordIsCorrect = true;
		actionIsCorrect = true;
		forceIsEnabled = false;

		/*
		 * Retrieves credentials to log on Jenkins server
		 */
		server = retrieveServer(args);
		username = retrieveUsername(args);
		password = retrievePassword(args);

		/*
		 * Initiates connection with Jenkins server.
		 */
		connectionManager = new ConnectionManager(server, username, password);
		jenkinsServer = connectionManager.logOnJenkins();

		/*
		 * Retrieves action and creates it. The creation step will help us to
		 * know if the command is correct.
		 */
		action = retrieveActionArgAndCreateAction(jenkinsServer, args,
				Constants.COPY_JOBS_REPLACING_PATTERNS_ARG,
				Constants.REMOVE_JOBS_ARG);
		if (action != null) {
			action.setServer(server);
			action.setUsername(username);
			action.setPassword(password);
		}

		return (serverIsCorrect && usernameIsCorrect && passwordIsCorrect && actionIsCorrect);
	}

	/**
	 * Retrieves server.
	 * 
	 * @param args
	 * @return the server argument value
	 */
	private String retrieveServer(String[] args) {
		String server = retrieveArgValue(args, Constants.SERVER_ARG);
		if (server == null) {
			serverIsCorrect = false;
		}
		return server;
	}

	/**
	 * Retrieves user name.
	 * 
	 * @param args
	 * @return the user name arg value
	 */
	private String retrieveUsername(String[] args) {
		String username = retrieveArgValue(args, Constants.USERNAME_ARG);
		if (username == null) {
			usernameIsCorrect = false;
		}
		return username;
	}

	/**
	 * Retrieves password.
	 * 
	 * @param args
	 * @return the password arg value
	 */
	private String retrievePassword(String[] args) {
		String password = retrieveArgValue(args, Constants.PASSWORD_ARG);
		if (password == null) {
			passwordIsCorrect = false;
		}
		return password;
	}

	/**
	 * Retrieves arg value.
	 * 
	 * @param args
	 * @param argName
	 * @return the argument value
	 */
	private String retrieveArgValue(String[] args, String argName) {
		String argValue = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equalsIgnoreCase(argName)) {
				argValue = args[i + 1];
				if (argValue.charAt(0) != '-') {
					return argValue;
				}
			}
		}
		return argValue;
	}

	/**
	 * Retrieves action arg.
	 * 
	 * @param args
	 * @param actionArgs
	 * @return
	 * @throws UnknownActionException
	 * @throws EmptyRegexpException
	 * @throws IOException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveViewsException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 */
	private Action retrieveActionArgAndCreateAction(
			JenkinsServer jenkinsServer, String[] args, String... actionArgs)
			throws UnknownActionException, EmptyRegexpException,
			NullServerJenkinsException, SeveralJobsWithSameNameInViewException,
			CantRetrieveJobsException, CantRetrieveViewsException {

		Action action = null;

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			for (String actionArg : actionArgs) {
				if (arg.equalsIgnoreCase(actionArg)) {
					action = ActionHelpers.createAction(args, arg, i,
							jenkinsServer);
					break; // 1 action per command
				}
			}
		}

		if (action != null) {
			forceIsEnabled = action.isExecute();
		} else {
			actionIsCorrect = false;
		}

		return action;
	}

	/**
	 * Does user confirm.
	 * 
	 * @return true if user confirms, false otherwise
	 */
	private boolean doesUserConfirm() {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		String userAnswer = null;

		// Read answer
		try {

			do {
				System.out.println(Constants.ASK_CONFIRMATION_MESSAGE);
				userAnswer = bufferedReader.readLine();
			} while (!userAnswer.equalsIgnoreCase(Constants.YES_ANSWER)
					&& !userAnswer.equalsIgnoreCase(Constants.NO_ANSWER));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (userAnswer.equalsIgnoreCase(Constants.YES_ANSWER)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Print help.
	 */
	public void printHelp() {
		System.out.println(Constants.HELP_MESSAGE);
	}

	/**
	 * Gets the jenkins server.
	 * 
	 * @return the jenkins server
	 */
	public JenkinsServer getJenkinsServer() {
		return jenkinsServer;
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
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUsername() {
		return username;
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
	 * Gets the action.
	 * 
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Is server is correct.
	 * 
	 * @return true if server arg is correct, false otherwise
	 */
	public boolean isServerIsCorrect() {
		return serverIsCorrect;
	}

	/**
	 * Is user name is correct.
	 * 
	 * @return true if user name is correct, false otherwise
	 */
	public boolean isUsernameIsCorrect() {
		return usernameIsCorrect;
	}

	/**
	 * Is password is correct.
	 * 
	 * @return true if password is true, false otherwise
	 */
	public boolean isPasswordIsCorrect() {
		return passwordIsCorrect;
	}

	/**
	 * Is action is correct.
	 * 
	 * @return true if action is correct, false otherwise
	 */
	public boolean isActionIsCorrect() {
		return actionIsCorrect;
	}

	/**
	 * Is force is enabled.
	 * 
	 * @return true if force is enabled, false otherwise
	 */
	public boolean isForceIsEnabled() {
		return forceIsEnabled;
	}

}
