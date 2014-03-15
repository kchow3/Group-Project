package cs.ualberta.ca.tunein;

import android.app.Application;

/**
 * Model
 * User Class:
 * This class is a model of the user, which is anyone
 * viewing the app.
 * To access this class throughout the whole app you can use 
 * shared preferences.
 */
public class User{

	private String name;
	private String uniqueID; 
	
	public User(String name, String id)
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

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	
}
