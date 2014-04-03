package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileViewActivity extends Activity {

	//public string that tags the extra of the comment to be edited that is passed to EditPageActivity
	public final static String EXTRA_USERID = "cs.ualberta.ca.tunein.userid";
	
	Commenter user;
	UserController userController;
	
	//page elemnts
	private TextView textViewProfileName;
	private TextView textViewProfileEmail;
	private TextView textViewProfileFacebook;
	private TextView textViewProfileTwitter;
	private TextView textViewProfileBio;
	private Button buttonProfileSave;
	private ImageView imageViewProfileImage;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    user = new Commenter();
	    userController = new UserController(user);
	    getInputUser();
	    setupPage();
	}
	
	private void setupPage()
	{
		textViewProfileName = (TextView) findViewById(R.id.textViewProfileName);
		textViewProfileEmail = (TextView) findViewById(R.id.textViewProfileEmail);
		textViewProfileFacebook = (TextView) findViewById(R.id.textViewProfileFacebook);
		textViewProfileTwitter = (TextView) findViewById(R.id.textViewProfileTwitter);
		textViewProfileBio = (TextView) findViewById(R.id.textViewProfileBio);
		buttonProfileSave = (Button) findViewById(R.id.buttonProfileSave);
		imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
		
		textViewProfileName.setText(user.getName());
		textViewProfileEmail.setText(user.getEmail());
		textViewProfileFacebook.setText(user.getFacebook());
		textViewProfileTwitter.setText(user.getTwitter());
		textViewProfileBio.setText(user.getBio());
		imageViewProfileImage.setImageBitmap(user.getImg().getBitMap());
		
		if(userController.checkCurrentUser(ProfileViewActivity.this, user.getUniqueID()))
		{
			buttonProfileSave.setVisibility(View.VISIBLE);
		}
		else
		{
			textViewProfileName.setFocusable(false);
			textViewProfileName.setClickable(false);
		}
	}
	
	private void getInputUser()
	{
		Intent intent = getIntent();
		String userid = (String) intent.getSerializableExtra(EXTRA_USERID);
		userController.loadProfile(userid);
	}

}
