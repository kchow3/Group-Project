package cs.ualberta.ca.tunein;

import java.util.ArrayList;

/**
 * Model
 * Thread Class:
 * This class is part of the comment model and holds a
 * list of comments.
 */
public class Thread 
{
	private ArrayList<Comment> discussionThread;

	/**
	 * Constructor that constructs a new list of comments.
	 */
	public Thread() 
	{
		this.discussionThread = new ArrayList<Comment>();
	}

	public ArrayList<Comment> getDiscussionThread() 
	{
		return discussionThread;
	}

	public void setDiscussionThread(ArrayList<Comment> discussionThread) 
	{
		this.discussionThread = discussionThread;
	}

}
