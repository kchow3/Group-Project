package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Controller
 * ThreadController Class:
 * This is part of the controller that controls a list of
 * comments and modifies the comments in the list passed to
 * the controller.
 */
public class ThreadController {

	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	
	private ThreadList discussionThread;
	
	/**
	 * Constructor constructs a contoller for the list of comments.
	 * @param threadList List of comments that will be modified.
	 */
	public ThreadController(ThreadList threadList) {
	
		discussionThread = threadList;
	}

	/**
	 * Method to create a topic comment with image.
	 * @param cntxt The appication context
	 * @param title Title of comment.
	 * @param comment Text of comment.
	 * @param img The image of the comment.
	 */
	public void createTopicImg(Context cntxt, String title, String comment, Bitmap bmp) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(cntxt);
    	String id = userCntrl.loadUserid(cntxt);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(cntxt);
		
		Image img = new Image(bmp);
		
		Comment aComment = new Comment(user, title, comment, loc, img, "0");
		list.add(aComment);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);
	}
	
	/**
	 * Method to create a topic comment with no image.
	 * @param cntxt The appication context
	 * @param title Title of comment.
	 * @param comment Text of comment.
	 */
	public void createTopic(Context cntxt, String title, String comment) 
	{	
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(cntxt);
    	String id = userCntrl.loadUserid(cntxt);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(cntxt);
		
		Comment aComment = new Comment(user, title, comment, loc, "0");
		list.add(aComment);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);
	}
	
	/**
	 * Retrieve the list of topic comments that are currently
	 * on elasticsearch.
	 * @param cntxt The appication context
	 * @return The sorted discussion thread.
	 */
	public void getOnlineTopics(Context cntxt) {
		SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		String sortName = prefs.getString("cs.ualberta.ca.tunein.sort", "default");
		// get comments from elastic search
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.getTopicsBySort(discussionThread, cntxt, sortName);
	}
	
	/** Method to check if the user has internet connection.
	 * Code from:
	 * http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts
	 * @param cntxt Context of the application
	 * @return the result of the check
	 */
	public boolean isNetworkAvailable(Context cntxt) {
		ConnectivityManager cm = (ConnectivityManager) cntxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) 
		{
			return true;
		} 
		else 
		{
			// toast message to tell user that they need internet
			CharSequence text = "No Internet Connection";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
		return false;
	}
}
