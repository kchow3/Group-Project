package cs.ualberta.ca.tunein;

import java.util.ArrayList;

/**
 * Model
 * CommentCache Class:
 * This class is for modeling the comment cache,
 * which will be a array list of comments that
 * the user chooses to save.
 * This class is not yet complete.
 */
public class CommentCache {

	private ArrayList<Comment> cache;
	
	/**
	 * Constructor constructs a new comment cache list.
	 */
	public CommentCache()
	{
		this.cache = new ArrayList<Comment>();
	}
}
