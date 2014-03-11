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
	 * This method loads the user info when there
	 * is a save of user info.
	 */
	public void loadUser();
	
	/**
	 * This method changes the username of the
	 * user using the app.
	 * @param name The new username.
	 */
	public void changeUsername(String name, Activity act);
}
