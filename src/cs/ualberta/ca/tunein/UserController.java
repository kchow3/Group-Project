package cs.ualberta.ca.tunein;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Controller
 * UserController Class:
 * This is a controller class that modifies the user model
 * and will also be used to load a saved username.
 */
public class UserController {
	
	private Commenter user;
	
	/**
	 * Constructor for controller to save and load in the current user's name and id
	 */
	public UserController() {
		this.user = new Commenter();
	}
	
	/**
	 * Construcotr for controller to check userid with profile id and
	 * loading in the profile.
	 * @param aUser The user to be modified
	 */
	public UserController(Commenter aUser)
	{
		this.user = aUser;
	}

	public String loadUsername(Context cntxt) {
		return user.getCurrentName(cntxt);
	}
	
	public String loadUserid(Context cntxt) {
		return user.getCurrentUniqueID(cntxt);
	}
	
	public void saveUserid(Context cntxt) {
		//setup an unique id for the user that is attached to the phone
		final TelephonyManager tm = (TelephonyManager) cntxt.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = "" + tm.getDeviceId();
		String androidId = "" + android.provider.Settings.Secure.getString(cntxt.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		String id = deviceId + androidId;
		user.setCurrentUniqueID(id, cntxt);
	}

	public void changeUsername(String name, Context cntxt) {
		user.setCurrentName(name, cntxt);
	}
	
	public boolean checkCurrentUser(Context cntxt, String userID)
	{
		//id of the current viewer
		Commenter aUser = new Commenter();
		String currentID = aUser.getCurrentUniqueID(cntxt);
		return user.getUniqueID().equals(currentID);
	}
	
	public void loadProfile(String userID)
	{
		ElasticSearchOperations eso = new ElasticSearchOperations();
		
	}

}
