package cs.ualberta.ca.tuneintest;

import cs.ualberta.ca.tunein.TopicListActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;


public class topicListActivityTest extends ActivityInstrumentationTestCase2<TopicListActivity> {
	
	Activity activity;
	
	public topicListActivityTest() {
		super(TopicListActivity.class);
	}
	
	/**public void testCreateButton() {
		
		activity = getActivity();
		final Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonCreate);
		
		activity.runOnUiThread(new Runnable()
	    {
	        public void run()
	        {
	            button.performClick();
	            
	        }
	    });
	}**/
//Need to add test to see if create comment button opens a dialog box for creating topics
}