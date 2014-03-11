package cs.ualberta.ca.tunein;

import android.app.Activity;

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
	public void loadUser() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void saveUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeUsername(String name, Activity act) {
		((User) act.getApplication()).setName(name);
	}

}
