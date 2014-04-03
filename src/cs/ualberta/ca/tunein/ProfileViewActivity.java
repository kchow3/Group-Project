package cs.ualberta.ca.tunein;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileViewActivity extends Activity {
	
	public static int SELECT_PICTURE_REQUEST_CODE = 12345;

	//public string that tags the extra of the comment to be edited that is passed to EditPageActivity
	public final static String EXTRA_USERID = "cs.ualberta.ca.tunein.userid";
	
	private Commenter user;
	private UserController userController;
	private String userid;
	
	//uri for image
	private Uri outputFileUri;
	
	//page elemnts
	private TextView textViewProfileName;
	private TextView textViewProfileEmail;
	private TextView textViewProfileFacebook;
	private TextView textViewProfileTwitter;
	private TextView textViewProfileBio;
	private Button buttonProfileSave;
	private Button buttonProfileUploadImage;
	private ImageView imageViewProfileImage;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    user = new Commenter();
	    userController = new UserController(user);
	    getInputUser();
	}
	
	@Override
	public void onResume()
	{
		//on resume to load profile if profile is updated when app resumed
		userController.loadProfile(userid);
	    setupPage();
	}
	
	/*
	 * Code from:
	 * http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    if(resultCode == RESULT_OK)
	    {
	        if(requestCode == SELECT_PICTURE_REQUEST_CODE)
	        {
	            final boolean isCamera;
	            if(data == null)
	            {
	                isCamera = true;
	            }
	            else
	            {
	                final String action = data.getAction();
	                if(action == null)
	                {
	                    isCamera = false;
	                }
	                else
	                {
	                    isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	                }
	            }

	            Uri selectedImageUri;
	            if(isCamera)
	            {
	                selectedImageUri = outputFileUri;
	            }
	            else
	            {
	                selectedImageUri = data == null ? null : data.getData();
	            }
	            try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
					imageViewProfileImage.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Method to setup the page's user interface
	 * Code from:
	 * http://stackoverflow.com/questions/660151/how-to-replicate-androideditable-false-in-code
	 */
	private void setupPage()
	{
		textViewProfileName = (TextView) findViewById(R.id.textViewProfileName);
		textViewProfileEmail = (TextView) findViewById(R.id.textViewProfileEmail);
		textViewProfileFacebook = (TextView) findViewById(R.id.textViewProfileFacebook);
		textViewProfileTwitter = (TextView) findViewById(R.id.textViewProfileTwitter);
		textViewProfileBio = (TextView) findViewById(R.id.textViewProfileBio);
		buttonProfileSave = (Button) findViewById(R.id.buttonProfileSave);
		buttonProfileUploadImage = (Button) findViewById(R.id.buttonProfileUploadImage);
		imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
		
		textViewProfileName.setText(user.getName());
		textViewProfileEmail.setText(user.getEmail());
		textViewProfileFacebook.setText(user.getFacebook());
		textViewProfileTwitter.setText(user.getTwitter());
		textViewProfileBio.setText(user.getBio());
		imageViewProfileImage.setImageBitmap(user.getAvatar().getBitMap());
		
		//check if current user for permission to update profile
		if(userController.checkCurrentUser(ProfileViewActivity.this, user.getUniqueID()))
		{
			buttonProfileSave.setVisibility(View.VISIBLE);
			buttonProfileUploadImage.setVisibility(View.VISIBLE);
			
			buttonProfileSave.setOnClickListener(profileSaveBtnClick);
			buttonProfileUploadImage.setOnClickListener(profileImageBtnClick);
		}
		else
		{
			textViewProfileName.setFocusable(false);
			textViewProfileName.setClickable(false);
			
			textViewProfileEmail.setFocusable(false);
			textViewProfileEmail.setClickable(false);
			
			textViewProfileFacebook.setFocusable(false);
			textViewProfileFacebook.setClickable(false);
			
			textViewProfileTwitter.setFocusable(false);
			textViewProfileTwitter.setClickable(false);
			
			textViewProfileBio.setFocusable(false);
			textViewProfileBio.setClickable(false);
			
			textViewProfileBio.setFocusable(false);
			textViewProfileBio.setClickable(false);
			
		}
	}
	
	/**
	 * Method to get the user id from intent to load in profile
	 */
	private void getInputUser()
	{
		Intent intent = getIntent();
		userid = (String) intent.getSerializableExtra(EXTRA_USERID);
	}
	
	/**
	 * Click listener to upload an image as profile avatar
	 */
	private OnClickListener profileImageBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	//get image upload
			ImageController imgCntrl = new ImageController(ProfileViewActivity.this);
			outputFileUri = imgCntrl.openImageIntent();
	    }
	};
	
	/**
	 * Click listener to save the profile.
	 */
	private OnClickListener profileSaveBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	//get text fields
			String name = textViewProfileName.getText().toString();
			String email = textViewProfileEmail.getText().toString();
			String facebook = textViewProfileFacebook.getText().toString();
			String twitter = textViewProfileTwitter.getText().toString();
			String bio = textViewProfileBio.getText().toString();
			//build bitmap
			imageViewProfileImage.buildDrawingCache(true);
	        Bitmap bitmap = imageViewProfileImage.getDrawingCache(true).copy(Config.RGB_565, false);
	        userController.saveProfile(name, email, facebook, twitter, bio, bitmap);
	        
			//toast massage to confirm profile save
			Context context = getApplicationContext();
			CharSequence text = "Profile Saved";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }
	};
	

}
