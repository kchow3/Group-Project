package cs.ualberta.ca.tunein;

import android.app.Activity;

/**
 * Controller
 * CommenterControllerInterface Class:
 * This class is an interface for the CommenterController.
 * The purpose of this class is to modify the Commenter model
 * and load/check user credentials.
 */
public interface CommenterControllerInterface {

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
	public void changeUsername(String name);
	
	/**
	 * This method is for checking if the commenter's 
	 * user id matches the comment creator's id so that
	 * they can edit the comment.
	 * @param id The unique id of a commenter.
	 * @param act The activity that calls this controller.
	 */
	public boolean checkValid(String commentID, Activity act);
}
