package cs.ualberta.ca.tuneintest;


import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CommentController;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.Commenter;

public class commentControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentControllerTest() {
		super(MainActivity.class);
	}
	
	public void testEquals() {
		Commenter commenter = new Commenter("myName", "ID");
		GeoLocation loc = new GeoLocation(0, 0);
		Comment comment0 = new Comment(commenter , "Title", "Comment", loc);
		Comment comment1 = new Comment(commenter , "Title", "Comment", loc);

		CommentController commentcontroller = new CommentController(comment0);
		commentcontroller.editText("Different comment");
		
		assertFalse("Edited text should be different", comment0.equals(comment1));
		
	}
}
