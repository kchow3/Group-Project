package cs.ualberta.ca.tuneintest;

import java.util.Date;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.CommentViewAdapter;
import cs.ualberta.ca.tunein.TopicListActivity;

public class commentViewAdapterTest extends ActivityInstrumentationTestCase2<TopicListActivity> {
	
	Activity activity;
	
	public commentViewAdapterTest() {
		super(TopicListActivity.class);
	}
	
	public void testviewingcomment() {
		activity = getActivity();
		
		//ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.TopicListActivity.class.getName(), null, false);
		ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.CommentPageActivity.class.getName(), null, false);
		
		//Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.otherLocation_button);
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonView);
		TouchUtils.clickView(this, button);
		
		cs.ualberta.ca.tunein.CommentPageActivity secondActivity = (cs.ualberta.ca.tunein.CommentPageActivity) monitor
		          .waitForActivityWithTimeout(5);
		assertNotNull(secondActivity);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
	
//Need to add tests for all properties of the CommentViewAdapter class

}