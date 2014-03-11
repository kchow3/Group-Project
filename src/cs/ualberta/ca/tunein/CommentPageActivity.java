package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
		
		
		
	}

}
