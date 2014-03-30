package cs.ualberta.ca.tunein;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View
 * CommentPageActivity Class:
 * Part of the view class that contains a comment that is
 * editable. To access the edit comment page the user has
 * to be a comment author and will have the option to click
 * edit when viewing a comment.
 * Dialog code from:
 * http://stackoverflow.com/questions/4279787/how-can-i-pass-values-between-a-dialog-and-an-activity
 */
public class EditPageActivity extends Activity {
	
	public static int SELECT_PICTURE_REQUEST_CODE = 12345;

	//public string that tags the extra of the comment that is passed to EditPageActivity
	public final static String EXTRA_EDIT = "cs.ualberta.ca.tunein.commentEdit";
	
	//comment passed through intent when clicking on a view comment button
	private Comment aComment;
	
	//picture added, false by default
	private boolean imgAdded = false;;
	
	//path of the image file
	private Uri outputFileUri;
	
	//variables for setting up textviews/buttons/imageview
	private TextView textViewEditTitle;
	private TextView textViewEditComment;
	private TextView textViewEditX;
	private TextView textViewEditY;
	private Button buttonEditImage;
	private Button buttonEditLocation;
	private Button buttonEditCancel;
	private Button buttonEditSubmit;
	private ImageView imageViewEditImage;
	
	//dialog elements
	private View createView;
	private TextView inputLong;
	private TextView inputLat;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_comment_view);
		getInputComment();
		setupComment();
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
					imageViewEditImage.setImageBitmap(bitmap);
					imageViewEditImage.setVisibility(View.VISIBLE);
					imgAdded = true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * This method grabs the intent passed to this activity
	 * from the activity that calls this page.
	 */
	private void getInputComment()
	{
		Intent intent = getIntent();
		this.aComment = (Comment) intent.getSerializableExtra(EXTRA_EDIT);
	}
	
	/**
	 * This method sets up all the xml elements (text, buttons and images)
	 *  in the edit comment view.
	 */
	private void setupComment()
	{
		textViewEditTitle = (TextView)findViewById(R.id.textViewEditTitle);
		textViewEditComment = (TextView)findViewById(R.id.textViewEditComment);
		textViewEditX = (TextView)findViewById(R.id.textViewEditX);
		textViewEditY = (TextView)findViewById(R.id.textViewEditY);
		
		buttonEditImage = (Button)findViewById(R.id.buttonEditImage);
		buttonEditLocation = (Button)findViewById(R.id.buttonEditLocation);
		buttonEditCancel = (Button)findViewById(R.id.buttonEditCancel);
		buttonEditSubmit = (Button)findViewById(R.id.buttonEditSubmit);
		
		imageViewEditImage = (ImageView)findViewById(R.id.imageViewEditImage);
		
		textViewEditTitle.setText(aComment.getTitle());
		textViewEditComment.setText(aComment.getComment());
		textViewEditX.setText(String.valueOf(aComment.getGeolocation().getLongitude()));
		textViewEditY.setText(String.valueOf(aComment.getGeolocation().getLatitude()));
		
		//if there is image load image else invisible
		if(aComment.isHasImage())
		{
			imageViewEditImage.setImageBitmap(aComment.getImg().getBitMap());
			imageViewEditImage.setVisibility(View.VISIBLE);
		}
		
		buttonEditImage.setOnClickListener(imageBtnClick);
		buttonEditLocation.setOnClickListener(locationBtnClick);
		buttonEditCancel.setOnClickListener(cancelBtnClick);
		buttonEditSubmit.setOnClickListener(submitBtnClick);
	}
	
	private OnClickListener imageBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {    	
			ImageController imgCntrl = new ImageController(EditPageActivity.this);
			outputFileUri = imgCntrl.openImageIntent();
	    }
	};
	
	/**
	 * Click listener for changing a location.
	 */
	private OnClickListener locationBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	setupDialogs();
			AlertDialog dialog = new AlertDialog.Builder(EditPageActivity.this)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String lon = inputLong.getText().toString();
			            String lat = inputLat.getText().toString();
			            textViewEditX.setText(lon);
			            textViewEditY.setText(lat);
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};

	/**
	 * Cancel edit button click listener.
	 */
	private OnClickListener cancelBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	Intent returnIntent = new Intent();
	    	setResult(RESULT_CANCELED,returnIntent);     
	    	finish();
	    }
	};

	/**
	 * Edit the comment with confirm click
	 * return intent code from:
	 * http://stackoverflow.com/questions/10407159/android-how-to-manage-start-activity-for-result
	 */
	private OnClickListener submitBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	CommentController commentController = new CommentController(aComment);
	    	//update the old favorite by removing old and adding new
	    	FavoriteController favoriteController = new FavoriteController(aComment);
	    	favoriteController.removeFromFav(getApplicationContext());
	    	
	    	if(imgAdded)
	    	{
		    	//get bitmap from the imageview
		    	imageViewEditImage.buildDrawingCache(true);
	        	Bitmap bitmap = imageViewEditImage.getDrawingCache(true).copy(Config.RGB_565, false);
	        	imageViewEditImage.destroyDrawingCache();  
	        	commentController.addImg(bitmap);
	    	}
        	//call cntrl to edit the comment
        	commentController.editTitle(textViewEditTitle.getText().toString());
        	commentController.editText(textViewEditComment.getText().toString());
        	commentController.changeLoc(Double.parseDouble(textViewEditX.getText().toString()),
	    			Double.parseDouble(textViewEditY.getText().toString()));
        	favoriteController.addtoFav(getApplicationContext());
        	commentController.updateElasticSearch();
	    	//return the comment through intent to comment view
	    	Intent returnIntent = new Intent();
	    	returnIntent.putExtra("editReturn", aComment);
	    	setResult(RESULT_OK,returnIntent);     
	    	finish();
	    }
	};

	private void setupDialogs()
	{
		LayoutInflater inflater = LayoutInflater.from(EditPageActivity.this);
		createView = inflater.inflate(R.layout.location_change, null);

		inputLong = (EditText) createView.findViewById(R.id.textViewInputChangeLong);
		inputLat = (EditText) createView.findViewById(R.id.textViewInputChangeLat);
	}
}
