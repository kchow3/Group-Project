package cs.ualberta.ca.tuneintest;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.CommentPageActivity;
import cs.ualberta.ca.tunein.ProfileViewActivity;

public class profileViewActivityTest extends ActivityInstrumentationTestCase2<CommentPageActivity> {
	
	public profileViewActivityTest() {
		super(CommentPageActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void profileDataTest() {
		//test if a profile displays a profile picture and personal information
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}