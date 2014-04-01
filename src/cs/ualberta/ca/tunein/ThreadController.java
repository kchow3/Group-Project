package cs.ualberta.ca.tunein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Controller
 * ThreadController Class:
 * This is part of the controller that controls a list of
 * comments and modifies the comments in the list passed to
 * the controller.
 */
public class ThreadController {

	private ThreadList discussionThread;
	private GeoLocation loc;
	
	/**
	 * Constructor constructs a contoller for the list of comments.
	 * @param threadList List of comments that will be modified.
	 */
	public ThreadController(ThreadList threadList) {
	
		discussionThread = threadList;
	}

	/**
	 * Method to create a topic comment with image.
	 * @param act The activity calling to create comment.
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
	 * @param act The activity calling to create comment.
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
	 * @param act The activity that calls this method
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
}
