package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

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

	//reply view adapter
	private ReplyViewAdapter viewAdapter;
	//comment passed through intent when clicking on a view comment button
	private Comment aComment;
	//reply list
	private ArrayList<Comment> replies;
	
	
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
		
		//setup the reply listview
		this.viewAdapter = new ReplyViewAdapter(this, replies);
		setContentView(R.layout.comment_view);
		ExpandableListView listview = (ExpandableListView) findViewById(R.id.expandableListViewReply);
		
		//setup
		listview.setAdapter(viewAdapter);
	}

}
