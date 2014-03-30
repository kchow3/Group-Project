package cs.ualberta.ca.tunein;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Model
 * User Class:
 * This class is a model of the user, which the person that 
 * is currently viewing the app.
 * To access this class throughout the whole app you can use 
 * shared preferences.
 */
public class User{

	private String name;
	private String uniqueID; 
	
	public User()
	{
	}
	
	public String getName(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.username", "Anonymous");
	}

	public void setName(String name, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.username", name).commit();
	}

	public String getUniqueID(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.userid", "");
	}

	public void setUniqueID(String uniqueID, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.userid", uniqueID).commit();
	}
	
}
