package cs.ualberta.ca.tunein;

import java.io.Serializable;
import java.util.Observable;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Model
 * Commenter Class:
 * This is a model class for the user that creates a comment.
 * This class is mainly used by getting passed in User model
 * info and uses that to create a comment author.
 */
public class Commenter extends Observable implements Serializable{
	
	//attributes for profile
	private String name;
	private String uniqueID; 
	private String email; 
	private String facebook; 
	private String twitter; 
	private String bio; 
	private Image avatar;
	private boolean hasImage = false;
	private boolean newProfile = true;;
	
	
	/**
	 * Constructor that constructs a commenter for the purpose of getting
	 * the current user.
	 */
	public Commenter() 
	{
	}
	
	/**
	 * Constructor that constructs a commenter.
	 * @param name The username of the commenter.
	 */
	public Commenter(String name, String id) 
	{
		this.name = name;
		this.uniqueID = id;
	}
	
	/**
	 * Constructor that constructs a commenter's profile.
	 * Use this constructor for posting new profile to elasticsearch.
	 */
	public Commenter(Context cntxt)
	{
		this.name = getCurrentName(cntxt);
		this.uniqueID = getCurrentUniqueID(cntxt);
		this.email = "";
		this.facebook = "";
		this.twitter = "";
		this.bio = "";
		this.hasImage = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueID() {
		return uniqueID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public boolean isNewProfile() {
		return newProfile;
	}

	public void setNewProfile(boolean newProfile) {
		this.newProfile = newProfile;
	}

	public String getCurrentName(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.username", "Anonymous");
	}

	public void setCurrentName(String name, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.username", name).commit();
	}

	public String getCurrentUniqueID(Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	return prefs.getString("cs.ualberta.ca.tunein.userid", "");
	}

	public void setCurrentUniqueID(String uniqueID, Context cntxt) {
    	SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
    	prefs.edit().putString("cs.ualberta.ca.tunein.userid", uniqueID).commit();
	}
	
	public void setupProfile(Commenter source) {
		this.name = source.getName();
		this.uniqueID = source.getUniqueID();
		this.email = source.getEmail();
		this.facebook = source.getFacebook();
		this.twitter = source.getTwitter();
		this.bio = source.getBio();
		this.hasImage = source.isHasImage();
		if(source.isHasImage())
		{
			this.avatar = source.getAvatar();
		}
		setChanged();
        notifyObservers(this);
	}

}
