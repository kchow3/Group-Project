package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	
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
		this.aComment = (Comment) intent.getSerializableExtra(EXTRA_COMMENT);
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
		textViewEditX.setText(String.format("%.5f", aComment.getGeolocation().getLongitude()));
		textViewEditY.setText(String.format("%.5f", aComment.getGeolocation().getLatitude()));
		
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
	    }
	};

	private OnClickListener cancelBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
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
	    	//change geolocation
	    	//change/add image
	    	finish();
	    }
	};


}
