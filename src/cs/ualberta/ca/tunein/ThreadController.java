package cs.ualberta.ca.tunein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

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
	private String sortName;
	
	/**
	 * Constructor constructs a contoller for the list of comments.
	 * @param threadList List of comments that will be modified.
	 */
	public ThreadController(ThreadList threadList) {
	
		discussionThread = threadList;
	}
	
	/**
	 * Method to sort topics by location.
	 */
	public void sortByLocation() 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Method to sort topics by set location.
	 */
	public void sortBySetLocation() 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Method to sort topics by pictures
	 */
	public void sortByPicture() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method to sort thread list by date.
	 * Code taken from:
	 * http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
	 */
	public void sortByDate() 
	{
		Collections.sort(discussionThread.getDiscussionThread(), new Comparator<Comment>() {
			  public int compare(Comment o1, Comment o2) {
			      return o2.getDate().compareTo(o1.getDate());
			  }
			});	
	}
	
	/**
	 * Method to sort topics by a scoring system.
	 */
	public void sortByScore()
	{
		
	}
	
	/**
	 * Method that sorts the topic list based on the current 
	 * sort criteria.
	 * @param act The activity that calls this method
	 */
	public void sortChooser(Activity act)
	{
		SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		sortName = prefs.getString("cs.ualberta.ca.tunein.sort", "Freshness");
		if(sortName.equals("My Location"))
			sortByLocation();
		if(sortName.equals("Set Location"))
			sortBySetLocation();
		if(sortName.equals("Picture"))
			sortByPicture();
		if(sortName.equals("Date"))
			sortByDate();
		if(sortName.equals("Score"))
			sortByScore();
		//if(sortName.equals("Freshness"))
			//ElasticSearchOperations.getCommentPostsByReplyCount(discussionThread, act);
	}

	/**
	 * Method to create a topic comment with image.
	 * @param act The activity calling to create comment.
	 * @param title Title of comment.
	 * @param comment Text of comment.
	 * @param img The image of the comment.
	 */
	public void createTopicImg(Activity act, String title, String comment, Bitmap bmp) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(act);
		
		Image img = new Image(bmp);
		Comment aComment = new Comment(user, title, comment, loc, img, "0");
		list.add(aComment);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);;
		//sortChooser(act);
	}
	
	/**
	 * Method to create a topic comment with no image.
	 * @param act The activity calling to create comment.
	 * @param title Title of comment.
	 * @param comment Text of comment.
	 */
	public void createTopic(Activity act, String title, String comment) 
	{	
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(act);
		
		Comment aComment = new Comment(user, title, comment, loc, "0");
		list.add(aComment);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);
		//sortChooser(act);
	}
	
	/**
	 * Retrieve the list of topic comments that are currently
	 * on elasticsearch.
	 * @param act The activity that calls this method
	 * @return The sorted discussion thread.
	 */
	public void getOnlineTopics(Activity act) {
		// get comments from elastic search
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.getCommentPostsByReplyCount(this.discussionThread, act);
		Log.v("topics this:", Integer.toString(this.discussionThread.getDiscussionThread().size()));
	}
}
