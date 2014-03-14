package cs.ualberta.ca.tuneintest;

import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.CommentController;
import cs.ualberta.ca.tunein.CommentPageActivity;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.EditPageActivity;
import cs.ualberta.ca.tunein.GeoLocation;
import cs.ualberta.ca.tunein.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class editPageActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	
	public editPageActivityTest() {
		super(MainActivity.class);
	}
	
	public void testEdit()
	{
		Commenter user = new Commenter("Bob", "testid29fniudofh");
		GeoLocation loc1 = new GeoLocation(1, 2);
		Comment aComment = new Comment(user, "Test", "This is a test", loc1);
		CommentController cntrl = new CommentController(aComment);
		cntrl.editTitle("Edit");
		cntrl.editText("This has been changed");
		GeoLocation loc2 = new GeoLocation(6, 5);
		cntrl.changeLoc(loc2);
		
		assertEquals("The title should now be 'Edit'", aComment.getTitle(), "Edit");
		assertNotSame("The title shouldn't now be 'Test'", aComment.getTitle(), "Test");
		assertEquals("The text should now be 'This has been changed'", aComment.getComment(), "This has been changed");
		assertNotSame("The title shouldn't now be 'This is a test'", aComment.getComment(), "This is a test");
		
		assertEquals("Longitude should be 6", aComment.getGeolocation().getLongitude(), loc2.getLongitude());
		assertNotSame("Longitude shouldn't be 1", aComment.getGeolocation().getLongitude(), loc1.getLongitude());
		
		assertEquals("Latitude should be 5", aComment.getGeolocation().getLatitude(), loc2.getLatitude());
		assertNotSame("Latitude shouldn't be 2", aComment.getGeolocation().getLatitude(), loc1.getLatitude());
	}
	
}
