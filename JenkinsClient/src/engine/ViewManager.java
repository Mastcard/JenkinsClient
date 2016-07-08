package engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.View;

import exception.CantRetrieveViewsException;
import exception.NullServerJenkinsException;

/**
 * The class ViewManager.
 * 
 * @author mastcard
 *
 */
public class ViewManager {

	/** The jenkins server. */
	private JenkinsServer jenkinsServer;
	
	/**
	 * Instantiates a new ViewManager.
	 * 
	 * @param jenkinsServer
	 * @throws NullServerJenkinsException 
	 */
	public ViewManager(JenkinsServer jenkinsServer) throws NullServerJenkinsException {
		if (jenkinsServer == null) {
			throw new NullServerJenkinsException();
		}
		
		this.jenkinsServer = jenkinsServer;
	}
	
	/**
	 * Gets views.
	 * 
	 * @param jenkinsServer
	 * @return the views
	 * @throws CantRetrieveViewsException 
	 * @throws IOException
	 */
	public Map<String, View> getViews() throws CantRetrieveViewsException  {
		Map<String, View> viewsMap = null;
		
		try {
			viewsMap = jenkinsServer.getViews();
		} catch (IOException e) {
			throw new CantRetrieveViewsException();
		}
		
		Map<String, View> result = new HashMap<String, View>();
		result.putAll(viewsMap);
		return result;
	}
	
}
