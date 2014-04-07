package cs.ualberta.ca.tunein;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Controller
 * UserController Class:
 * This is a controller class that modifies the user model
 * and will also be used to load a saved username.
 */
public class UserController {
	
	//tag for checking if there exists a profile for this user
	public final static String NEWPROFILE = "cs.ualberta.ca.tunein.profile";
	
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

	/**
	 * Method to get current user's username
	 * @param cntxt Context of app
	 * @return the username
	 */
	public String loadUsername(Context cntxt) {
		return user.getCurrentName(cntxt);
	}
	
	/**
	 * Method to get the current user's user id
	 * @param cntxt The appication context
	 * @return the current user id
	 */
	public String loadUserid(Context cntxt) {
		//
		return user.getCurrentUniqueID(cntxt);
	}
	
	/**
	 * Method to save the user id.
	 * @param cntxt The appication context
	 */
	public void saveUserid(Context cntxt) {
		//setup an unique id for the user that is attached to the phone
		final TelephonyManager tm = (TelephonyManager) cntxt.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = "" + tm.getDeviceId();
		String androidId = "" + android.provider.Settings.Secure.getString(cntxt.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		String id = deviceId + androidId;
		user.setCurrentUniqueID(id, cntxt);
	}

	/**
	 * Method to change the current users name
	 * @param name The new name.
	 * @param cntxt The appication context
	 */
	public void changeUsername(String name, Context cntxt) {
		user.setCurrentName(name, cntxt);
	}
	
	/**
	 * Check if the current user id is the one passed in.
	 * @param cntxt The appication context
	 * @param userID The comparing user id
	 * @return The result of the check
	 */
	public boolean checkCurrentUser(Context cntxt, String userID)
	{
		//id of the current viewer
		Commenter aUser = new Commenter();
		String currentID = aUser.getCurrentUniqueID(cntxt);
		return user.getUniqueID().equals(currentID);
	}
	
	/**
	 * Create a profile for the current user.
	 * @param cntxt The appication context
	 */
	public void createProfile(Context cntxt)
	{
		SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		boolean newProfile = prefs.getBoolean(NEWPROFILE, true);
		if(newProfile)
		{
			ElasticSearchOperations eso = new ElasticSearchOperations();
			//create a whole new profile
			Commenter commenter = new Commenter(cntxt);
			eso.postProfileModel(commenter);
			prefs.edit().putBoolean(NEWPROFILE, false).commit();
		}
	}
	
	/**
	 * Load the profile of a user that is clicked on
	 * @param userID The user id of the profile to load
	 * @param cntxt The appication context
	 */
	public void loadProfile(String userID, Context cntxt)
	{
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.getProfileModel(userID, user, cntxt);
	}
	
	/**
	 * Method to save a profile with avatar image.
	 * @param name The name of user
	 * @param email The email of the user
	 * @param facebook The facebook profile of user
	 * @param twitter The twitter username of user
	 * @param bio The biography of user
	 * @param bmp The avatar of user
	 */
	public void saveProfileImg(String name, String email, String facebook, String twitter, String bio, Bitmap bmp)
	{
		user.setName(name);
		user.setEmail(email);
		user.setFacebook(facebook);
		user.setTwitter(twitter);
		user.setBio(bio);
		user.setHasImage(true);
		user.setAvatar(bmp);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.putProfileModel(user);
	}
	
	/**
	 * Method to save a profile with no avatar image.
	 * @param name The name of user
	 * @param email The email of the user
	 * @param facebook The facebook profile of user
	 * @param twitter The twitter username of user
	 * @param bio The biography of user
	 */
	public void saveProfile(String name, String email, String facebook, String twitter, String bio)
	{
		user.setName(name);
		user.setEmail(email);
		user.setFacebook(facebook);
		user.setTwitter(twitter);
		user.setBio(bio);
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.putProfileModel(user);
	}
}
