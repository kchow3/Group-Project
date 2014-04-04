package cs.ualberta.ca.tuneintest;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.CommentViewAdapter;
import cs.ualberta.ca.tunein.TopicListActivity;

@SuppressLint("NewApi")
public class commentViewAdapterTest extends ActivityInstrumentationTestCase2<TopicListActivity> {
	
	Activity activity;
	Instrumentation instrumentation;
	
	public commentViewAdapterTest() {
		super(TopicListActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
		 instrumentation = getInstrumentation();
		 activity = getActivity();
		 
	}
	
	public void testViewingCommentButton() {
		activity = getActivity();
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonView);
		assertNotNull("Button should not be null", button);
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.CommentPageActivity.class.getName(), null, false);
		
		TouchUtils.clickView(this, button);
		
		cs.ualberta.ca.tunein.CommentPageActivity secondActivity = (cs.ualberta.ca.tunein.CommentPageActivity) monitor
		          .waitForActivityWithTimeout(5);
		assertNotNull(secondActivity);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
	
	public void testReplyButton(){
		activity = getActivity();
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonReply);
		TouchUtils.clickView(this, button);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
		
	}
	
	
	
//Need to add tests for all properties of the CommentViewAdapter class

}