package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Controller
 * UserController Class:
 * This is a controller class that modifies the user model
 * and will also be used to load a  saved username.
 */
public class UserController implements UserControllerInterface {
	
	public UserController() {
	}

	@Override
	public String loadUsername(Activity act) {
    	SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.username", "Anonymous");
	}
	
	@Override
	public String loadUserid(Activity act) {
    	SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.userid", "");
	}
	
	@Override
	public void saveUserid(String id, Activity act) {
    	SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.userid", id).commit();
	}

	@Override
	public void changeUsername(String name, Activity act) {
    	SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.username", name).commit();
	}

}
