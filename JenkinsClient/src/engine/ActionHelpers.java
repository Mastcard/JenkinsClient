package engine;

import java.util.ArrayList;
import java.util.List;

import model.Action;
import model.Pattern;
import util.Constants;
import console.CopyJobsReplacingPatternsAction;
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
	 */
	public static Action createAction(String[] args, String actionArg, int i) throws UnknownActionException {
		Action action = null;
		int forceActionIndex = 0;
		
		switch (actionArg) {
			case Constants.COPY_JOBS_REPLACING_PATTERNS_ARG:
				action = createCopyJobsReplacingPatternsAction(args, i);
				forceActionIndex = i + 3;
				break;
			case Constants.REMOVE_JOBS_ARG:
				
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
	 */
	private static Action createCopyJobsReplacingPatternsAction(String[] args, int i) {
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
						Pattern pattern = new Pattern(patternTab[0], patternTab[1]);
						patterns.add(pattern);
					}
				}
				
				if (!patterns.isEmpty()) {
					action = new CopyJobsReplacingPatternsAction(jobsRegexp, patterns);
				}
				
			}
			
		}
		
		return action;
	}
	
}
