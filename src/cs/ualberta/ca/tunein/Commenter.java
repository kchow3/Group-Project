package cs.ualberta.ca.tunein;

import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Model
 * Commenter Class:
 * This is a model class for the user that creates a comment.
 * This class is mainly used by getting passed in User model
 * info and uses that to create a comment author.
 */
public class Commenter implements Serializable{

	private String name;
	private String uniqueID; 
	
	/**
	 * Constructor that constructs a commenter for the purpose of getting
	 * the current user.
	 */
	public Commenter() 
	{
	}
	
	/**
	 * Constructor that constructs a commenter.
	 * @param name The username of the commenter.
	 */
	public Commenter(String name, String id) 
	{
		this.name = name;
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
	
	public String getCurrentName(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.username", "Anonymous");
	}

	public void setCurrentName(String name, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.username", name).commit();
	}

	public String getCurrentUniqueID(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.userid", "");
	}

	public void setCurrentUniqueID(String uniqueID, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.userid", uniqueID).commit();
	}

}
