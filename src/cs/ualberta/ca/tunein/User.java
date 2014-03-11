package cs.ualberta.ca.tunein;

import android.app.Application;

/**
 * Model
 * User Class:
 * This class is a model of the user, which is anyone
 * viewing the app.
 * To access this class throughout the whole app you can use 
 * String username = ((User) this.getApplication()).getName();
 * to retrieve the username and 
 * String id = ((User) this.getApplication()).getUniqueID();
 * to get the id. The this is referring to the activity.
 */
public class User extends Application {

	private String name;
	private String uniqueID; 
	
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
