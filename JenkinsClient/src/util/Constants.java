package util;

/**
 * Constants.
 * 
 * @author mastcard
 *
 */
public class Constants {

	/* ########################## */
	/* #### BASIC CONSTANTS. #### */
	/* ########################## */
	
	/** The PROJECT NAME. */
	public static final String PROJECT_NAME = "Jenkins Client";
	
	/** The MODE STRING. */
	private static final String MODE_STRING = "Mode";
	
	/** The CONSOLE MODE STRING. */
	private static final String CONSOLE_STRING = "Console";
	
	/** The USER INTERFACE MODE STRING. */
	private static final String USER_INTERFACE_STRING = "User Interface";
	
	/** The CONSOLE MODE STRING. */
	public static final String CONSOLE_MODE_STRING = MODE_STRING + " : " + CONSOLE_STRING;
	
	/** The USER INTERFACE MODE STRING. */
	public static final String USER_INTERFACE_MODE_STRING = MODE_STRING + " : " + USER_INTERFACE_STRING;
	
	
	/* ############################ */	
	/* #### JENKINS CONSTANTS. #### */
	/* ############################ */
	
	/** The ALL JOBS VIEW NAME. */
	public static final String ALL_JOBS_VIEW_NAME = "All";

	
	/* ############################# */	
	/* #### ARGUMENT CONSTANTS. #### */
	/* ############################# */
	
	/** The SERVER ARG. */
	public static final String SERVER_ARG = "-server";
	
	/** The USERNAME ARG. */
	public static final String USERNAME_ARG = "-username";
	
	/** The PASSWORD ARG. */
	public static final String PASSWORD_ARG = "-password";
	
	/** The FORCE ACTION ARG. */
	public static final String FORCE_ACTION_ARG = "-f";
	
	/** The COPY JOBS REPLACING PATTERNS ARG. */
	public static final String COPY_JOBS_REPLACING_PATTERNS_ARG = "-copyJobsReplacingPatterns";
	
	/** The REMOVE JOBS ARG. */
	public static final String REMOVE_JOBS_ARG = "-removeJobs";
	
	/** The ALL UNUSED OPTION. */
	public static final String ALL_UNUSED_JOBS_OPTION = "unused";
	
	/** The HELP ARG. */
	public static final String HELP_ARG = "-help";

	
	/* ############################ */	
	/* #### CONSOLE CONSTANTS. #### */
	/* ############################ */
	
	/** The COPY JOBS REPLACING PATTERNS RESULT PREVIEW MESSAGE. */
	public static final String CJRP_RESULT_PREVIEW_MESSAGE = "Result preview :";
	
	/** The REMOVE JOBS RESULT PREVIEW MESSAGE. */
	public static final String RJ_RESULT_PREVIEW_MESSAGE = "The following jobs will be definitely deleted :";
	
	/** The YES ANSWER. */
	public static final String YES_ANSWER = "y";
	
	/** The NO ANSWER. */
	public static final String NO_ANSWER = "n";
	
	/** The ASK CONFIRMATION MESSAGE. */
	public static final String ASK_CONFIRMATION_MESSAGE = "Do you want to perform the action ? [" + YES_ANSWER + "|" + NO_ANSWER + "]";
	
	/** The COPY JOBS REPLACING PATTERNS USAGE MESSAGE. */
	public static final String CJRP_USAGE_MESSAGE = "\t" + COPY_JOBS_REPLACING_PATTERNS_ARG + " [regexp:jobs to copy] [before1/after1,before2/after2,...]";
	
	/** The REMOVE JOBS USAGE MESSAGE. */
	public static final String RJ_USAGE_MESSAGE = "\t" + REMOVE_JOBS_ARG + " [regexp:jobs to remove]\n\t" + REMOVE_JOBS_ARG + " [unused]";
	
	/** The FORCE USAGE MESSAGE. */
	public static final String FORCE_USAGE_MESSAGE = "\tExecutes action without confirmation : -f";
	
	/** The HELP MESSAGE. */
	public static final String HELP_MESSAGE = "This client is used to log on a Jenkins server and executes the following actions :\n"
			+ "\tcopy jobs replacing patterns : " + CJRP_USAGE_MESSAGE + "\n"
			+ "\t				  remove jobs : " + RJ_USAGE_MESSAGE + "\n"
			+ "\t                        help : " + HELP_ARG + "\n"
			+ FORCE_USAGE_MESSAGE + "\n\n"
			+ "\tEXAMPLE : java -jar jenkins_client.jar " + SERVER_ARG + " HOST:PORT " + USERNAME_ARG + " USERNAME " + PASSWORD_ARG + " PASSWORD " 
			+ CJRP_USAGE_MESSAGE + " .*trunk-dev.* trunk-dev/4.2-SP02_Patch_COR,...";
	
	
	/* ########################## */	
	/* #### LABEL CONSTANTS. #### */
	/* ########################## */
	
	/** The SERVER LABEL. */
	public static final String SERVER_LABEL = "Server";
	
	/** The USER LABEL. */
	public static final String USER_LABEL = "User";
	
	/** The PASSWORD LABEL. */
	public static final String PASSWORD_LABEL = "Password";
	
	
	/* ################################# */
	/* #### ACTION LABEL CONSTANTS. #### */
	/* ################################# */
	
	/** The CONNECT ACTION LABEL. */
	public static final String CONNECT_ACTION_LABEL = "Connect";
	
	/** The OK ACTION LABEL. */
	public static final String OK_ACTION_LABEL = "OK";
	
}
