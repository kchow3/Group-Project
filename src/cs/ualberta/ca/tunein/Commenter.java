package cs.ualberta.ca.tunein;

import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Model
 * Commenter Class:
 * This is a model class for the user that creates a comment.
 * This class is mainly used by getting passed in User model
 * info and uses that to create a comment author.
 */
public class Commenter implements Serializable{

	private String name;
	private String uniqueID; 
	private String email; 
	private String facebook; 
	private String twitter; 
	private String bio; 
	private Image avatar;
	
	private String elasticID;
	
	//attributes for profile
	
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

	public String getElasticID() {
		return elasticID;
	}

	public void setElasticID(String elasticID) {
		this.elasticID = elasticID;
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

}
