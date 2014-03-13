package cs.ualberta.ca.tuneintest;

import cs.ualberta.ca.tunein.TopicListActivity;
//import junit.framework.TestCase;
import android.app.AlertDialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;


public class topicListActivityTest extends ActivityInstrumentationTestCase2<TopicListActivity> {
	
	public topicListActivityTest() {
		super(TopicListActivity.class);
	}
	
	public void testCreateButton() {
		Button button = (Button) getActivity().findViewById(cs.ualberta.ca.tunein.R.id.buttonCreate);
		button.callOnClick();
		
	}
//Need to add test to see if create comment button opens a dialog box for creating topics
}
