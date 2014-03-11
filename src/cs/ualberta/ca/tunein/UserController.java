package cs.ualberta.ca.tunein;

import android.app.Activity;

public class UserController implements UserControllerInterface {
	
	public UserController() {
	}

	@Override
	public void loadUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeUsername(String name, Activity act) {
		((User) act.getApplication()).setName(name);
	}

	@Override
	public boolean checkValid(String commentID, Activity act) {
		String currentID = ((User) act.getApplication()).getUniqueID();
		
		return commentID.equals(currentID);
	}


}
