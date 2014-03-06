package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

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
	
	    // TODO Auto-generated method stub
	}

}
