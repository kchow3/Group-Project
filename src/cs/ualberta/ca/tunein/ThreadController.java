package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

import android.app.Activity;
import android.view.View;

/**
 * Controller
 * ThreadController Class:
 * This is part of the controller that controls a list of
 * comments and modifies the comments in the list passed to
 * the controller.
 */
public class ThreadController implements ThreadControllerInterface {

	private ThreadList discussionThread;
	
	/**
	 * Constructor constructs a contoller for the list of comments.
	 * @param threadList List of comments that will be modified.
	 */
	public ThreadController(ThreadList threadList) {
	
		discussionThread = threadList;
	}

	@Override
	public void sortByLocation(GeoLocation loc) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortBySetLocation(GeoLocation loc) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortByPicture() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortByDate() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTopicImg(Activity act, String title, String comment, Image img) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation(20, 30);
		
		Comment aComment = new Comment(user, title, comment, loc, img);
		list.add(aComment);
		ElasticSearchOperations.postCommentModel(aComment);
	}
	
	@Override
	public void createTopic(Activity act, String title, String comment) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation(20, 30);
		
		Comment aComment = new Comment(user, title, comment, loc);
		list.add(aComment);
		ElasticSearchOperations.postCommentModel(aComment);
	}
	
	@Override
	public void getOnlineTopics(Activity act) {
		// get comments from elastic search
		ElasticSearchOperations.getCommentPosts(discussionThread, act);
	}
}
