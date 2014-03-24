package cs.ualberta.ca.tunein;

import android.content.Context;

/**
 * Controller
 * UserController Class:
 * This is a controller class that modifies the user model
 * and will also be used to load a  saved username.
 */
public class UserController {
	
	private User user;
	
	public UserController() {
	}

	public String loadUsername(Context cntxt) {
		return user.getName(cntxt);
	}
	
	public String loadUserid(Context cntxt) {
		return user.getUniqueID(cntxt);
	}
	
	public void saveUserid(String id, Context cntxt) {
		user.setUniqueID(id, cntxt);
	}

	public void changeUsername(String name, Context cntxt) {
		user.setName(name, cntxt);
	}

}
