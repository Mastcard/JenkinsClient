package test;

/**
 * The class TestsConstants (used for tests).
 * 
 * @author mastcard
 *
 */
class TestsConstants {

	/* ########################## */
	/* #### BASIC CONSTANTS. #### */
	/* ########################## */
	
	public static final String SERVER = "http://localhost:8080/";
	public static final String USERNAME = "mastcard";
	public static final String PASSWORD = "password";
	
	public static final String WRONG_SERVER = "http://localhost:8888";
	public static final String WRONG_USERNAME = "username";
	public static final String WRONG_PASSWORD = "Password";
	

	/* ######################### */
	/* #### COPY CONSTANTS. #### */
	/* ######################### */
	
	public static final String UNUSED_JOB_NAME = "unused job";
	
	
	/* ######################### */
	/* #### COPY CONSTANTS. #### */
	/* ######################### */
	
	public static final String BRANCH_PATTERN_BEFORE = "trunk-dev";
	public static final String BRANCH_PATTERN_AFTER = "4.2-SP02_Patch_COR";
	public static final String JOB_TO_COPY_NAME = "(win64_x64) " + BRANCH_PATTERN_BEFORE + " Fetch+Compile";
	public static final String COPIED_JOB_NAME = "(win64_x64) " + BRANCH_PATTERN_AFTER + " Fetch+Compile";
	
	public static final String WRONG_JOB_TO_COPY_NAME = "(win64_x64) unfound job";
	
}
