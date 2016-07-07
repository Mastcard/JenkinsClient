package main;

import java.io.IOException;
import java.net.URISyntaxException;

import console.ConsoleModeMain;
import exception.EmptyRegexpException;
import exception.JenkinsConnectionFailedException;
import exception.JobNotFoundException;
import exception.NoJobsToCopyException;
import exception.NoPatternsException;
import exception.NullServerNameException;
import exception.UnknownActionException;
import exception.WrongCommandException;

/**
 * Launches JenkinsClient in console mode.
 * 
 * @author mastcard
 *
 */
public class JenkinsClientConsole {

	public static void main(String[] args) {
		ConsoleModeMain consoleModeMain = new ConsoleModeMain();
		
		try {
			consoleModeMain.executeCommand(args);
		} catch (WrongCommandException | UnknownActionException | IOException
				| EmptyRegexpException | URISyntaxException
				| JenkinsConnectionFailedException | JobNotFoundException
				| NoJobsToCopyException | NoPatternsException | NullServerNameException e) {
			e.toString();
			System.out.println();
			consoleModeMain.printHelp();
			System.exit(1);
		}
	}

}
