package cs.ualberta.ca.tunein;

import java.util.ArrayList;

/**
 * Model
 * Favorites Class:
 * This is using a singleton design pattern.
 * This class contains a list of all comments that are favorited
 * by the user. Adding to favorites is through commentcontroller.
 * Code from:
 * http://stackoverflow.com/questions/7885276/how-to-share-same-data-between-multiple-activities-in-android?lq=1
 */
public class Favorites {

	private static final Favorites INSTANCE = new Favorites();
	
	public ArrayList<Comment> favorites;
	public ArrayList<String> favoriteIDs;
	
    // Private constructor prevents instantiation from other classes
    private Favorites() {}

    public static Favorites getInstance() {
        return INSTANCE;
    }

}
