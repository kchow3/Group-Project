package cs.ualberta.ca.tunein;

import java.util.ArrayList;

public class Thread 
{
	private ArrayList<Comment> discussionThread;

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
