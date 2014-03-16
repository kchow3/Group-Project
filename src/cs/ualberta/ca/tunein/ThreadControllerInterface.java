package cs.ualberta.ca.tunein;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import android.app.Activity;
import android.view.View;

/**
 * Controller
 * ThreadControllerInterface Class:
 * This is the interface for the ThreadController and 
 * is implemented by controllers to modify comments in
 * a list.
 */
public interface ThreadControllerInterface {
	
	/**
	 * This method creates a new topic comment with image in the list.
	 * @param aComment The comment to be added.
	 */
	public void createTopicImg(Activity act, String title, String comment, Image img);
	
	/**
	 * This method creates a new topic comment with no image in the list.
	 * @param aComment The comment to be added.
	 */
	public void createTopic(Activity act, String title, String comment);
	
	/**
	 * This method sorts the list of comments by the
	 * user's location.
	 * @param loc The location that the comments will be sorted.
	 */
	public void sortByLocation(GeoLocation loc);
	
	/**
	 * This method sorts the list of comments by a set
	 * location.
	 * @param loc The location that the comments will be sorted.
	 */
	public void sortBySetLocation(GeoLocation loc);
	
	/**
	 * This method sorts the list of comments by the criteria
	 * of having pictures and date.
	 */
	public void sortByPicture();
	
	/**
	 * This method sorts the list of comments by the date
	 * of comments.
	 */
	public void sortByDate();
	
	/**
	 * This method gets the comments from elastics search
	 * 
	 * @param act
	 *            The activty of the listview to load into.
	 */
	public void getOnlineTopics(Activity act);

}
