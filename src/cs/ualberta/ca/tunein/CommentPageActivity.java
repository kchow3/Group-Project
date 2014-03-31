package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View
 * CommentPageActivity Class:
 * Part of the view class that contains a comment and its replies.
 * This is part of the view when a user presses a view button on a 
 * comment to bring up this page.
 * Dialog code from:
 * http://stackoverflow.com/questions/4279787/how-can-i-pass-values-between-a-dialog-and-an-activity
 * Intent code from:
 * http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android
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
	//reply list
	private ArrayList<Comment> replies;
	//comment controller
	private CommentController commentController;
	//favorite controller
	private FavoriteController favoriteController;
	//cache controller
	private CacheController cacheController;
	//boolean to check if current comment is repy to reply
	private boolean isReplyReply;
	
	//variables for setting up textviews/buttons/imageview
	private TextView textViewCommentTitle;
	private TextView textViewCommentUser;
	private TextView textViewCommentDate;
	private TextView textViewCommentFavCount;
	private TextView textViewCommentReplyCount;
	private TextView textViewCommentBlock;
	private TextView textViewCommentSaved;
	private TextView textViewCommentFaved;
	
	private Button buttonCommentFav;
	private Button buttonCommentSave;
	private Button buttonCommentEdit;
	private Button buttonCommentReply;
	
	private ImageView imageViewCommentImage;
	
	//dialog elements
	private View createView;
	private TextView inputTitle;
	private TextView inputComment;
	private ImageView inputImage;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.replies = new ArrayList<Comment>();
	    getInputComment();
	    setContentView(R.layout.comment_view);
	    favoriteController = new FavoriteController();
	    cacheController = new CacheController();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		setupComment();
		
		//setup the reply listview
		this.viewAdapter = new ReplyViewAdapter(this, replies);
		ExpandableListView listview = (ExpandableListView) findViewById(R.id.expandableListViewReply);
		
		commentController = new CommentController(aComment, viewAdapter);
		commentController.loadCommentReplies(this);
		replies = aComment.getReplies();
		//setup
		listview.setAdapter(viewAdapter);
		viewAdapter.updateReplyView(replies);
		collapseAll(listview);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				aComment = (Comment) data.getSerializableExtra("editReturn");
				commentController = new CommentController(aComment, viewAdapter);
				commentController.loadCommentReplies(this);
				setupComment();
				viewAdapter.updateReplyView(aComment.getReplies());
			}
		     
		     if (resultCode == RESULT_CANCELED) {
		    	 //edit cancelled
		     }
		}
	}

	/**
	 * Method to get input from intents.
	 */
	private void getInputComment()
	{
		Intent intent = getIntent();
		isReplyReply = intent.getBooleanExtra("isReplyReply", false);
		aComment = (Comment) intent.getSerializableExtra(EXTRA_COMMENT);
	}
	
	/**
	 * Setup elements on the CommentPage
	 */
	private void setupComment()
	{
		//setup comment info
		textViewCommentTitle = (TextView)findViewById(R.id.textViewCommentTitle);
		textViewCommentUser = (TextView)findViewById(R.id.textViewCommentUser);
		textViewCommentDate = (TextView)findViewById(R.id.textViewCommentDate);
		textViewCommentFavCount = (TextView)findViewById(R.id.textViewCommentFavCount);
		textViewCommentReplyCount = (TextView)findViewById(R.id.textViewCommentReplyCount);
		textViewCommentBlock = (TextView)findViewById(R.id.textViewCommentBlock);
		textViewCommentFaved = (TextView)findViewById(R.id.textViewCommentFaved);
		textViewCommentSaved = (TextView)findViewById(R.id.textViewCommentSaved);
		
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
		
		//if there is image load image else invisible
		if(aComment.isHasImage())
		{
			imageViewCommentImage.setImageBitmap(aComment.getImg().getBitMap());
			imageViewCommentImage.setVisibility(View.VISIBLE);
		}
		
		commentController = new CommentController(aComment);
		if(commentController.checkValid(this))
		{
			buttonCommentEdit.setVisibility(View.VISIBLE);
		}
		
		//check if comment is favorited
		if(favoriteController.inFav(aComment))
		{
			textViewCommentFaved.setVisibility(View.VISIBLE);
		}
		
		//check if comment is saved
		if(cacheController.inCache(aComment))
		{
			textViewCommentSaved.setVisibility(View.VISIBLE);
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
	    	favoriteController.addToFav(getApplicationContext(), aComment);
	    }
	};
	
	/**
	 * This click listener will saved the comment to user's cache.
	 */
	private OnClickListener saveBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	cacheController.addToCache(getApplicationContext(), aComment);
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
	 * This click listener will go to reply page to create a reply comment
	 * to the comment that is being viewed.
	 * Bitmap code from:
	 * http://stackoverflow.com/questions/8490474/cant-compress-a-recycled-bitmap
	 */
	private OnClickListener replyBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	setupDialogs();
			AlertDialog dialog = new AlertDialog.Builder(CommentPageActivity.this)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String title = inputTitle.getText().toString();
			            String text = inputComment.getText().toString();
			            
			            commentController = new CommentController(aComment);
			            //create comment with image else one with no image
			            if (inputImage.getVisibility() == View.VISIBLE) 
			            {
			            	inputImage.buildDrawingCache(true);
			            	Bitmap bitmap = inputImage.getDrawingCache(true).copy(Config.RGB_565, false);
			            	inputImage.destroyDrawingCache();           	
			            	commentController.addReplyImg(aComment.getElasticID(), CommentPageActivity.this, title, text, bitmap, isReplyReply);
			            } 
			            else 
			            {	                
			            	commentController.addReply(aComment.getElasticID(), CommentPageActivity.this, title, text, isReplyReply);
			            }
			            replies = aComment.getReplies();
			            viewAdapter.updateReplyView(replies);
		        		setupComment();
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};
	
	/**
	 * This method is for setting up the dialog boxes.
	 */
	private void setupDialogs()
	{
		LayoutInflater inflater = LayoutInflater.from(CommentPageActivity.this);
		createView = inflater.inflate(R.layout.create_comment_view, null);

		inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
		inputComment = (EditText) createView.findViewById(R.id.editTextComment);
		inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
	}
	
	
	/**
	 * Method for collapsing all parent items in the listview.
	 * Code from:
	 * http://stackoverflow.com/questions/2848091/expandablelistview-collapsing-all-parent-items
	 * @param listview The listview to be collapsed.
	 */
	private void collapseAll(ExpandableListView listview)
	{
		int count = viewAdapter.getGroupCount();
		for(int i = 0; i < count; i++)
		{
			listview.collapseGroup(i);
		}
	}
}
