package cs.ualberta.ca.tuneintest;

import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.CommentController;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CommentPageActivity;

public class commentPageActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentPageActivityTest() {
		super(MainActivity.class);
	}
	
	private MainActivity activity;
	
	protected void setUp() throws Exception {
		 super.setUp();
		 setActivityInitialTouchMode(false);
		 activity = getActivity();
	}
	
	public void testOnCreate() throws Exception { //this test currently fails
		ArrayList<Comment> replies;
		replies = new ArrayList<Comment>();
		assertTrue("List of replies should be visible", replies.equals(null));
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
//Need to add tests for all properties of the CommentPageActivity class
	
//Most methods and attributes of CommentPageActivity class are private
//Must change to protected if testing is required for these methods and attributes
}