package main;

import console.ConsoleModeMain;
import exception.UnknownActionException;
import exception.WrongCommandException;
import util.Constants;

/**
 * Launches JenkinsClient in console mode.
 * 
 * @author mastcard
 *
 */
public class JenkinsClientConsole {

	public static void main(String[] args) {
		System.out.println(Constants.PROJECT_NAME + " (" + Constants.CONSOLE_MODE_STRING + ")");
		ConsoleModeMain consoleModeMain = new ConsoleModeMain();
		
		try {
			consoleModeMain.executeCommand(args);
		} catch (WrongCommandException | UnknownActionException e) {
			e.printStackTrace();
		}
	}

}
