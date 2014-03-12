package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * View
 * CommentPageActivity Class:
 * Part of the view class that contains a comment and its replies.
 * This is part of the view when a user presses a view button on a 
 * comment to bring up this page.
 * TODO: Send an intent of comment properties to another instance 
 * of this class when user selects view button on a reply and open that
 * comment.
 */
public class CommentPageActivity extends Activity {

	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	
	//reply view adapter
	private ReplyViewAdapter viewAdapter;
	//comment passed through intent when clicking on a view comment button
	private Comment aComment;
	//reply list
	private ArrayList<Comment> replies;
	
	//variables for seeting up textviews/buttons/imageview
	private TextView textViewCommentTitle;
	private TextView textViewCommentUser;
	private TextView textViewCommentDate;
	private TextView textViewCommentFavCount;
	private TextView textViewCommentReplyCount;
	private TextView textViewCommentFaved;
	private TextView textViewCommentSaved;
	private TextView textViewCommentBlock;
	
	private Button buttonCommentFav;
	private Button buttonCommentSave;
	private Button buttonCommentEdit;
	private Button buttonCommentReply;
	
	private ImageView imageViewCommentImage;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.replies = new ArrayList<Comment>();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		setContentView(R.layout.comment_view);
		getInputComment();
		setupComment();
		
		//setup the reply listview
		this.viewAdapter = new ReplyViewAdapter(this, replies);
		ExpandableListView listview = (ExpandableListView) findViewById(R.id.expandableListViewReply);
		
		//setup
		listview.setAdapter(viewAdapter);
	}
	
	private void getInputComment()
	{
		Intent intent = getIntent();
		this.aComment = (Comment) intent.getSerializableExtra(EXTRA_COMMENT);
	}
	
	private void setupComment()
	{
		//setup comment info
		textViewCommentTitle = (TextView)findViewById(R.id.textViewCommentTitle);
		textViewCommentUser = (TextView)findViewById(R.id.textViewCommentUser);
		textViewCommentDate = (TextView)findViewById(R.id.textViewCommentDate);
		textViewCommentFavCount = (TextView)findViewById(R.id.textViewCommentFavCount);
		textViewCommentReplyCount = (TextView)findViewById(R.id.textViewCommentReplyCount);
		textViewCommentFaved = (TextView)findViewById(R.id.textViewCommentFaved);
		textViewCommentSaved = (TextView)findViewById(R.id.textViewCommentSaved);
		textViewCommentBlock = (TextView)findViewById(R.id.textViewCommentBlock);
		
		buttonCommentFav = (Button)findViewById(R.id.buttonCommentFav);
		buttonCommentSave = (Button)findViewById(R.id.buttonCommentSave);
		buttonCommentEdit = (Button)findViewById(R.id.buttonCommentEdit);
		buttonCommentReply = (Button)findViewById(R.id.buttonCommentReply);
		
		imageViewCommentImage = (ImageView)findViewById(R.id.imageViewCommentImage);
		
		textViewCommentTitle.setText(aComment.getTitle());
		textViewCommentBlock.setText(aComment.getComment());
		textViewCommentUser.setText(aComment.getCommenter().getName());
		textViewCommentDate.setText(aComment.dateToString());
		textViewCommentFavCount.setText("Favs: " + Integer.toString(aComment.getFavoriteCount()));
		textViewCommentReplyCount.setText("Replies: " + Integer.toString(aComment.getReplyCount()));
		
		//Saved! text if comment is saved.
		if(aComment.isFavorited())
		{
			textViewCommentFaved.setVisibility(View.VISIBLE);
		}
		
		//Faved! text if comment is favorited.
		if(aComment.isSaved())
		{
			textViewCommentSaved.setVisibility(View.VISIBLE);
		}
		
		//if there is image load image else invisible
		if(aComment.isHasImage())
		{
			imageViewCommentImage.setVisibility(View.VISIBLE);
		}
		
		CommentController cntrl = new CommentController(aComment);
		if(cntrl.checkValid(this))
		{
			buttonCommentEdit.setVisibility(View.VISIBLE);
		}
		
		buttonCommentFav.setOnClickListener(favBtnClick);
		buttonCommentSave.setOnClickListener(saveBtnClick);
		buttonCommentEdit.setOnClickListener(editBtnClick);
		buttonCommentReply.setOnClickListener(replyBtnClick);
	}
	
	/**
	 * This click listener will add the comment to user's favorites.
	 */
	private OnClickListener favBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    }
	};
	
	/**
	 * This click listener will saved the comment to user's cache.
	 */
	private OnClickListener saveBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    }
	};
	
	/**
	 * This click listener will go to edit page to edit the comment
	 * if they are the comment author.
	 */
	private OnClickListener editBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    }
	};
	
	/**
	 * This click listner will go to reply page to create a reply comment
	 * to the comment that is being viewed.
	 */
	private OnClickListener replyBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	LayoutInflater inflater = LayoutInflater.from(CommentPageActivity.this);
			final View createView = inflater.inflate(R.layout.create_comment_view, null);

			final TextView inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
			final TextView inputComment = (EditText) createView.findViewById(R.id.editTextComment);
			final ImageView inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
			
			AlertDialog dialog = new AlertDialog.Builder(CommentPageActivity.this)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String title = inputTitle.getText().toString();
			            String text = inputComment.getText().toString();
			            
			            //create comment with image else one with no image
			            if (inputImage.getVisibility() == View.VISIBLE) 
			            {
			            	inputImage.buildDrawingCache();
			            	Bitmap bmp = inputImage.getDrawingCache();
			            	Image img = new Image(bmp);
		            	
			        		//temp geo location
			            	String username = ((User)getApplication()).getName();
			            	String id = ((User) getApplication()).getUniqueID();
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		Comment newComment  = new Comment(user, title, text, loc, img);
			        		CommentController cntrl = new CommentController(aComment);
			        		cntrl.addReply(newComment);
			        		
			        		replies = aComment.getReplies();
			        		viewAdapter.updateReplyView(replies);
			            } 
			            else 
			            {	                
			            	//temp geo location
			            	String username = ((User)getApplication()).getName();
			            	String id = ((User) getApplication()).getUniqueID();
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		Comment newComment  = new Comment(user, title, text, loc);
			        		CommentController cntrl = new CommentController(aComment);
			        		cntrl.addReply(newComment);
			        		
			        		replies = aComment.getReplies();
			        		viewAdapter.updateReplyView(replies);
			            }
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};

}
