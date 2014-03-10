package cs.ualberta.ca.tunein;

import android.app.Application;

/**
 * Model
 * Commenter Class:
 * This is a model class for the user that creates a comment.
 * This class is not complete yet. The only attributes right now
 * is a name for the commenter. To access this class throughout the
 * whole app you can use 
 * String username = ((Commenter) this.getApplication()).getName();
 * to retrieve the username and 
 * String id = ((Commenter) this.getApplication()).getUniqueID();
 * to get the id.
 */
public class Commenter extends Application{

	private String name;
	private String uniqueID; 
	
	/**
	 * Constructor that constructs a commenter.
	 * @param name The username of the commenter.
	 */
	public Commenter(String id) 
	{
		this.name = "Anonymous";
		this.uniqueID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueID() {
		return uniqueID;
	}
	

}
