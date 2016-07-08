package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.offbytwo.jenkins.JenkinsServer;

import model.Action;
import model.Pattern;
import util.Constants;
import console.CopyJobsReplacingPatternsAction;
import console.RemoveJobsAction;
import exception.CantRetrieveJobsException;
import exception.CantRetrieveViewsException;
import exception.EmptyRegexpException;
import exception.NullServerJenkinsException;
import exception.SeveralJobsWithSameNameInViewException;
import exception.UnknownActionException;

/**
 * The class ActionHelpers.
 * 
 * @author mastcard
 *
 */
public class ActionHelpers {

	/**
	 * Creates action.
	 * 
	 * @param args
	 * @param actionArg
	 * @param i
	 * @return the action
	 * @throws UnknownActionException
	 * @throws EmptyRegexpException
	 * @throws IOException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveJobsException
	 * @throws CantRetrieveViewsException 
	 * @throws SeveralJobsWithSameNameInViewException 
	 */
	public static Action createAction(String[] args, String actionArg, int i,
			JenkinsServer jenkinsServer) throws UnknownActionException,
			EmptyRegexpException, NullServerJenkinsException,
			CantRetrieveJobsException, SeveralJobsWithSameNameInViewException, CantRetrieveViewsException {

		Action action = null;
		int forceActionIndex = 0;

		switch (actionArg) {
		case Constants.COPY_JOBS_REPLACING_PATTERNS_ARG:
			action = createCopyJobsReplacingPatternsAction(args, i,
					jenkinsServer);
			forceActionIndex = i + 3;
			break;
		case Constants.REMOVE_JOBS_ARG:
			action = createRemoveJobsAction(args, i, jenkinsServer);
			forceActionIndex = i + 2;
			break;
		default:
			throw new UnknownActionException(actionArg);
		}

		// Deduces if user wants the action to be performed without preview
		if (forceActionIndex < args.length) {
			String forceArg = args[forceActionIndex];
			if (forceArg.equals(Constants.FORCE_ACTION_ARG)) {
				action.setExecute(true);
			}
		}

		return action;
	}

	/**
	 * Creates copy jobs replacing patterns action.
	 * 
	 * @param args
	 * @param i
	 * @return the action
	 * @throws EmptyRegexpException
	 * @throws IOException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveJobsException
	 */
	private static Action createCopyJobsReplacingPatternsAction(String[] args,
			int i, JenkinsServer jenkinsServer) throws EmptyRegexpException,
			NullServerJenkinsException, CantRetrieveJobsException {

		Action action = null;
		List<Pattern> patterns = new ArrayList<Pattern>();

		if (i + 2 < args.length) {
			String jobsRegexp = args[i + 1];
			String patternsString = args[i + 2];

			if (jobsRegexp.charAt(0) != '-' && patternsString.charAt(0) != '-') {
				String[] patternsTab = patternsString.split(",");

				for (int k = 0; k < patternsTab.length; k++) {
					String patternsCouple = patternsTab[k];
					String[] patternTab = patternsCouple.split("/");
					if (patternTab.length == 2) {
						Pattern pattern = new Pattern(patternTab[0],
								patternTab[1]);
						patterns.add(pattern);
					}
				}

				if (!patterns.isEmpty()) {
					action = new CopyJobsReplacingPatternsAction(jenkinsServer,
							jobsRegexp, patterns);
				}

			}

		}

		return action;
	}

	/**
	 * Creates remove jobs action.
	 * 
	 * @param args
	 * @param i
	 * @param jenkinsServer
	 * @return the remove jobs action
	 * @throws IOException
	 * @throws EmptyRegexpException
	 * @throws NullServerJenkinsException
	 * @throws CantRetrieveJobsException
	 * @throws SeveralJobsWithSameNameInViewException
	 * @throws CantRetrieveViewsException 
	 */
	private static Action createRemoveJobsAction(String[] args, int i,
			JenkinsServer jenkinsServer) throws EmptyRegexpException,
			NullServerJenkinsException, SeveralJobsWithSameNameInViewException,
			CantRetrieveJobsException, CantRetrieveViewsException {

		Action action = null;

		if (i + 1 < args.length) {
			String regexp = args[i + 1];

			if (regexp.charAt(0) != '-') {
				action = new RemoveJobsAction(jenkinsServer, regexp);
			}
		}

		return action;
	}

}
