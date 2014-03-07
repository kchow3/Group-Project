package cs.ualberta.ca.tunein;

/**
 * Model
 * Commenter Class:
 * This is a model class for the user that creates a comment.
 * This class is not complete yet. The only attributes right now
 * is a name for the commenter. 
 * TODO:add a unique property to the
 * commenter class that is linked to their phone so that only they
 * can change thier comments.
 */
public class Commenter {

	private String name;
	
	/**
	 * Constructor that constructs a commenter.
	 * @param name The username of the commenter.
	 */
	public Commenter(String name) 
	{
		this.name = "Anonymous";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
