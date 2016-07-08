package main;

import console.ConsoleModeMain;
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
		} catch (WrongCommandException | UnknownActionException
				| EmptyRegexpException | JenkinsConnectionFailedException
				| JobNotFoundException | NoPatternsException | NullServerNameException
				| NullServerJenkinsException
				| SeveralJobsWithSameNameInViewException
				| CantRetrieveJobsException | CantRetrieveViewsException
				| WrongURISyntaxException e) {

			System.out.println(e.toString());
			System.out.println();
			consoleModeMain.printHelp();
			System.exit(1);
		} catch (NoJobsToCopyException | NoJobsToRemoveException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
	}

}
