package console;

import model.Action;
import engine.ActionHelpers;
import exception.UnknownActionException;
import exception.WrongCommandException;
import util.Constants;

/**
 * The Console mode main class.
 * 
 * @author mastcard
 *
 */
public class ConsoleModeMain {
	
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
	 */
	public void executeCommand(String[] args) throws WrongCommandException, UnknownActionException {

		// Check args
		if (!checkArgs(args)) {
			throw new WrongCommandException();
		}
		
		action.setServer(server);
		action.setUsername(username);
		action.setPassword(password);
		
		/*
		 * TODO 
		 */
		
	}
	
	/**
	 * Check args.
	 * 
	 * @param args
	 * @return true if command is correct, false otherwise
	 * @throws UnknownActionException 
	 */
	private boolean checkArgs(String[] args) throws UnknownActionException {
		serverIsCorrect = true;
		usernameIsCorrect = true;
		passwordIsCorrect = true;
		actionIsCorrect = true;
		forceIsEnabled = false;
		
		server = retrieveServer(args);
		username = retrieveUsername(args);
		password = retrievePassword(args);
		action = retrieveActionArg(args, Constants.COPY_JOBS_REPLACING_PATTERNS_ARG, Constants.REMOVE_JOBS_ARG);
		
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
				argValue = args[i+1];
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
	 */
	private Action retrieveActionArg(String[] args, String...actionArgs) throws UnknownActionException {
		Action action = null;
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			for (String actionArg : actionArgs) {
				if (arg.equalsIgnoreCase(actionArg)) {
					action = ActionHelpers.createAction(args, arg, i);
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
