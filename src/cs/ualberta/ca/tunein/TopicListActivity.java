package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TopicListActivity extends Activity {

	//comment view adapter
	private CommentViewAdapter viewAdapter;
	//discussion thread list
	private Thread threadList;
	//variables for adding topic
	private String title;
	private String comment;
	private Image img;
	private boolean hasImg;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    threadList = new Thread();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		//setup the comment listview
		viewAdapter = new CommentViewAdapter(this, threadList.getDiscussionThread());
		ListView listview = (ListView) findViewById(R.id.listViewTopics);
		
		//setup adapter
		listview.setAdapter(viewAdapter);
		
	}
	
	//create comment btn click opens dialog box for creating topics
	public void createCommentClick(View v)
	{
		LayoutInflater inflater = LayoutInflater.from(TopicListActivity.this);
		final View createView = inflater.inflate(R.layout.create_comment_view, null);

		final TextView inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
		final TextView inputComment = (EditText) createView.findViewById(R.id.editTextComment);
		final ImageView inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
		
		AlertDialog dialog = new AlertDialog.Builder(TopicListActivity.this)
		    .setTitle("Create Comment")
		    .setView(createView)
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            title = inputTitle.getText().toString();
		            comment = inputComment.getText().toString();
		            
		            if (inputImage.getVisibility() == View.VISIBLE) 
		            {
		            	inputImage.buildDrawingCache();
		            	Bitmap bmp = inputImage.getDrawingCache();
		            	//img = new Image(bmp);
		            	hasImg = true;
		            } 
		            else 
		            {
		                hasImg = false;
		            }
		        }
		    })
		    .setNegativeButton("Cancel", null).create();
		dialog.show();
		
		//temp user and geo location
		Commenter user = new Commenter("bob");
		GeoLocation loc = new GeoLocation(5, 10);
		
		ThreadController cntrl = new ThreadController(threadList);
		Comment newComment  = new Comment(user, this.title, this.comment, loc);
		cntrl.createTopic(newComment);
		
		viewAdapter.updateThreadView(threadList);
	}

}
