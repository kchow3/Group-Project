package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * View
 * CommentPageActivity Class:
 * Part of the view class that contains a comment and its replies.
 * This is part of the view when a user presses a view button on a 
 * comment to bring up this page.
 */
public class CommentPageActivity extends Activity {

	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	//public string that tags the extra of the comment to be edited that is passed to EditPageActivity
	public final static String EXTRA_EDIT = "cs.ualberta.ca.tunein.commentEdit";
	
	//reply view adapter
	private ReplyViewAdapter viewAdapter;
	//comment passed through intent when clicking on a view comment button
	private Comment aComment;
	//parent topic comment corresponding to the comment being viewed
	private Comment topicComment;
	//reply list
	private ArrayList<Comment> replies;
	
	//variables for setting up textviews/buttons/imageview
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
	    getInputComment();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		setContentView(R.layout.comment_view);
		setupComment();
		
		//setup the reply listview
		this.viewAdapter = new ReplyViewAdapter(this, replies, topicComment);
		ExpandableListView listview = (ExpandableListView) findViewById(R.id.expandableListViewReply);
		
		//setup
		listview.setAdapter(viewAdapter);
		viewAdapter.updateReplyView(replies);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         aComment = (Comment) data.getSerializableExtra("editResult"); 
		         setupComment();
		     }
		     if (resultCode == RESULT_CANCELED) {
		    	 //edit cancelled
		     }
		  }
		}
	
	private void getInputComment()
	{
		Intent intent = getIntent();
		this.aComment = (Comment) intent.getSerializableExtra(EXTRA_COMMENT);
		this.topicComment = (Comment) intent.getSerializableExtra(EXTRA_COMMENT);
		replies = aComment.getReplies();
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
	    	Intent intent = new Intent(getApplicationContext(), EditPageActivity.class);
	    	intent.putExtra(EXTRA_EDIT, aComment);
	    	startActivityForResult(intent, 1);
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
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername(CommentPageActivity.this);
			            	String id = userCntrl.loadUserid((Activity)CommentPageActivity.this);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		Comment newComment  = new Comment(user, title, text, loc, img);
			        		CommentController cntrl = new CommentController(aComment);
			        		cntrl.addReply(newComment);
			        		
			        		ElasticSearchOperations.putCommentModel(topicComment);
			     		        		
			        		replies = aComment.getReplies();
			        		viewAdapter.updateReplyView(replies);
			            } 
			            else 
			            {	                
			            	//temp geo location
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername(CommentPageActivity.this);
			            	String id = userCntrl.loadUserid((Activity)CommentPageActivity.this);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		Comment newComment  = new Comment(user, title, text, loc);
			        		CommentController cntrl = new CommentController(aComment);

			        		cntrl.addReply(newComment);
			        		Log.v("replies", Integer.toString(aComment.getReplyCount()));
			        		Log.v("topic replies", Integer.toString(topicComment.getReplyCount()));
			        		ElasticSearchOperations.putCommentModel(topicComment);
			     		        		
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
