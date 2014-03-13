package cs.ualberta.ca.tunein;

import java.util.ArrayList;

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
	public void createTopic(Comment aComment) 
	{
		ArrayList<Comment> list = discussionThread.getDiscussionThread();
		list.add(aComment);
	}
	
}
