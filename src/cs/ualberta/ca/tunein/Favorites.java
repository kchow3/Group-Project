package cs.ualberta.ca.tunein;

import java.util.ArrayList;

/**
 * Model
 * Favorites Class:
 * This class contains a list of all comments that are favorited
 * by the user. Adding to favorites is through commentcontroller.
 */
public class Favorites {
	
	//list of a users favorites.
	private ArrayList<Comment> favorites;

	/**
	 * Constructor constructs an array list of comments.
	 */
	public Favorites() {
		this.favorites = new ArrayList<Comment>();
	}

}
