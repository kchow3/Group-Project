package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TopicListActivity extends Activity {

	//comment view adapter
	private CommentViewAdapter viewAdapter;
	//discussion thread list
	private Thread threadList;
	
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

}
