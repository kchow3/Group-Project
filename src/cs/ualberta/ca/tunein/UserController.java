package cs.ualberta.ca.tunein;

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
	
	public UserController() {
		user = new Commenter();
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

}
