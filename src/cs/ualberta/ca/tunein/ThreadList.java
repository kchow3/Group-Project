package cs.ualberta.ca.tunein;

import java.util.ArrayList;
import java.util.Collection;

import android.widget.ArrayAdapter;

/**
 * Model
 * Thread Class:
 * This class is part of the comment model and holds a
 * list of comments.
 */
public class ThreadList 
{
	private ArrayList<Comment> discussionThread;
	private ArrayAdapter<Comment> adapter;

	/**
	 * Constructor that constructs a new list of comments.
	 */
	public ThreadList() 
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
	
	public void setAdapter(ArrayAdapter<Comment> adapter) {
		this.adapter = adapter;
	}
	
	public void addCommentCollection(Collection<Comment> posts) {
		this.discussionThread.addAll(posts);
		this.adapter.notifyDataSetChanged();
	}
	
	public void clear() {
		this.discussionThread.clear();
		this.adapter.notifyDataSetChanged();
	}
}
