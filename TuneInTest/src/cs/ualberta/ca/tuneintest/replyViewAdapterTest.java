package cs.ualberta.ca.tuneintest;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.CommentPageActivity;
import cs.ualberta.ca.tunein.TopicListActivity;

public class replyViewAdapterTest extends ActivityInstrumentationTestCase2<CommentPageActivity> {
	
	public replyViewAdapterTest() {
		super(CommentPageActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void viewRepliesTest() {
		//test if replies are displayed or hidden upon clicking the expand button
	}
	
	public void viewSingleReplyTest() {
		//test if clicking view on a reply brings up the comment view page
	}
	
	public void createReplyTest() {
		//test if clicking reply on a reply displays as a child of that reply
	}
	
	public void replyDataTest() {
		//test if reply authors profile is viewable from the reply window
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}