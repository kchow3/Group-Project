package cs.ualberta.ca.tunein;

import android.app.Activity;

/**
 * Controller
 * UserControllerInterface Class:
 * This class is an interface for the UserController.
 * The purpose of this class is to modify the User model
 * and load/check user credentials.
 */
public interface UserControllerInterface {

	/**
	 * This method loads the username info from
	 * shared preferences.
	 */
	public String loadUsername(Activity act);
	
	/**
	 * This method loads the userid info from
	 * shared preferences.
	 */
	public String loadUserid(Activity act);
	
	/**
	 * This method saves the userid info.
	 */
	public void saveUserid(String id, Activity act);
	
	/**
	 * This method changes the username of the
	 * user using the app.
	 * @param name The new username.
	 */
	public void changeUsername(String name, Activity act);
}
