package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileViewActivity extends Activity {

	
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
		
		
	}
	
	private void getInputUser()
	{
		
	}

}
