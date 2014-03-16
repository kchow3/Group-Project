package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
 */
public class EditPageActivity extends Activity {

	//public string that tags the extra of the comment that is passed to EditPageActivity
	public final static String EXTRA_EDIT = "cs.ualberta.ca.tunein.commentEdit";
	//public string that tags the extra of the topic comment that is passed to CommentPageActivity
	public final static String EXTRA_TOPIC_COMMENT = "cs.ualberta.ca.tunein.topicComment";
	
	//comment passed through intent when clicking on a view comment button
	private Comment aComment;
	
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
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		setContentView(R.layout.edit_comment_view);
		getInputComment();
		setupComment();
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
	    	//set visibility to VISIBLE after adding image
	    }
	};
	
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

	private OnClickListener cancelBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	Intent returnIntent = new Intent();
	    	setResult(RESULT_CANCELED,returnIntent);     
	    	finish();
	    }
	};

	private OnClickListener submitBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	CommentController cntrl = new CommentController(aComment);
	    	cntrl.editTitle(textViewEditTitle.getText().toString());
	    	cntrl.editText(textViewEditComment.getText().toString());
	    	cntrl.changeLoc(Double.parseDouble(textViewEditX.getText().toString()),
	    			Double.parseDouble(textViewEditY.getText().toString()));
	    	Intent returnIntent = new Intent();
	    	returnIntent.putExtra("editResult", aComment);
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
