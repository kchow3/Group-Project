package cs.ualberta.ca.tuneintest;

import cs.ualberta.ca.tunein.TopicListActivity;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;


public class topicListActivityTest extends ActivityInstrumentationTestCase2<TopicListActivity> {
	
	Activity activity;
	
	public topicListActivityTest() {
		super(TopicListActivity.class);
	}
	
	public void testCreateButton() {
		
		activity = getActivity();
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonCreate);
		TouchUtils.clickView(this, button);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
//Need to add test to see if create comment button opens a dialog box for creating topics
}