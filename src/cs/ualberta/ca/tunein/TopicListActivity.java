package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * View
 * TopicListActivity Class:
 * This is part of the view of displaying the topic list
 * of comments. This also includes the creation view of creating
 * topic comments through a dialog box.
 */
public class TopicListActivity extends Activity {

	//comment view adapter
	private CommentViewAdapter viewAdapter;
	//discussion thread list
	private ThreadList threadList;
	//thread controller
	private ThreadController cntrl;
	//string of the sort requirement
	private String sortType;
	//variables for adding topic
	private String title;
	private String comment;
	private Image img;
	
	private Button buttonMainMenu;
	private TextView textViewSort;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent intent = getIntent();
		sortType = intent.getStringExtra("SORT");
	    threadList = new ThreadList();
		setContentView(R.layout.topic_list_view);
	    cntrl = new ThreadController(threadList, sortType);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();

	    setupTopicView();
		threadList = cntrl.getOnlineTopics(this);
		//setup the comment listview
		viewAdapter = new CommentViewAdapter(TopicListActivity.this, threadList, sortType);
		ListView listview = (ListView) findViewById(R.id.listViewTopics);
		
		//setup adapter
		threadList.setAdapter(viewAdapter);
		listview.setAdapter(viewAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setupTopicView()
	{
		//setup buttons
	    buttonMainMenu = (Button)findViewById(R.id.buttonMainMenu);
		buttonMainMenu.setOnClickListener(mainmenuBtnClick);
		//setuptextview
		textViewSort = (TextView)findViewById(R.id.textViewSort);
		textViewSort.setText(sortType);
	}
	
	/**
	 * This method is to open the create topic comment dialog box
	 * and create a comment and add to the topic list.
	 * @param v The view passed in.
	 */
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
		            
		            //create comment with image 
		            if (inputImage.getVisibility() == View.VISIBLE) 
		            {
		            	inputImage.buildDrawingCache();
		            	Bitmap bmp = inputImage.getDrawingCache();
		            	img = new Image(bmp);
		        		cntrl.createTopicImg(TopicListActivity.this, title, comment, img);
		        		viewAdapter.updateThreadView(threadList);
		            } 
		            else 
		            {	                
		        		cntrl.createTopic(TopicListActivity.this, title, comment);
		        		viewAdapter.updateThreadView(threadList);        		
		            }
		        }
		    })
		    .setNegativeButton("Cancel", null).create();
		dialog.show();
	}

	/**
	 * This click listener will go to the main menu page.
	 */
	private OnClickListener mainmenuBtnClick = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
