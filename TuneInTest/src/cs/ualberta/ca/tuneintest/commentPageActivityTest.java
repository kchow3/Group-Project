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
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void viewCommentTest() {
		//test if a comment is viewable upon clicking the view button
	}
	
	public void createReplyTest() {
		//test if reply page opens upon clicking the reply button
	}
	
	public void uploadImageTest() {
		//test if upload image dialog opens upon click the upload image button
	}
	
	public void editCommentTest() {
		//test if the edit comment page opens upon clicking the edit button
	}
	
	public void favCommentTest() {
		//test if clicking the fav button displays a notification that a comment has been stored
	}
	
	public void saveCommentTest () {
		//test if clicking the save button displays a notification that a comment has been stored
	}
	
	public void viewProfileTest() {
		//test if a profile is viewable upon clicking a user ID
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
	
}