package cs.ualberta.ca.tunein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

import android.app.Activity;

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
	public ThreadController(ThreadList threadList , String sort) {
	
		discussionThread = threadList;
		sortName = sort;
	}

	public void sortByLocation() 
	{
		// TODO Auto-generated method stub
		
	}

	public void sortBySetLocation() 
	{
		// TODO Auto-generated method stub
	}

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
		ArrayList<Comment> thread = discussionThread.getDiscussionThread();
		Collections.sort(thread, new Comparator<Comment>() {
			  public int compare(Comment o1, Comment o2) {
			      return o2.getDate().compareTo(o1.getDate());
			  }
			});	
	}
	
	public void sortByScore()
	{
		
	}
	
	public void sortByFresh()
	{
		ArrayList<Comment> thread = discussionThread.getDiscussionThread();
		Collections.sort(thread, new Comparator<Comment>() {
			  public int compare(Comment o1, Comment o2) {
			      return o2.getReplyCount() - o1.getReplyCount();
			  }
			});	
	}
	
	public void sortChooser()
	{
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
		if(sortName.equals("Freshness"))
			sortByFresh();
	}

	public void createTopicImg(Activity act, String title, String comment, Image img) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(act);
		
		Comment aComment = new Comment(user, title, comment, loc, img);
		list.add(aComment);
		ElasticSearchOperations.postCommentModel(aComment);
		sortChooser();
	}
	
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
		
		Comment aComment = new Comment(user, title, comment, loc);
		list.add(aComment);
		ElasticSearchOperations.postCommentModel(aComment);
		sortChooser();
	}
	
	public void getOnlineTopics(Activity act) {
		// get comments from elastic search
		ElasticSearchOperations.getCommentPosts(discussionThread, act);
		sortChooser();
	}
}
