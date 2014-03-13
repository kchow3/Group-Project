package cs.ualberta.ca.tuneintest;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;

public class commentTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentTest() {
		super(MainActivity.class);
	}
	
	public void testEquals() {
		Commenter commenter = new Commenter("myName", "ID");
		GeoLocation loc = new GeoLocation(0, 0);
	
		Comment comment0 = new Comment(commenter , "Sample Title", "sample comment", loc);
		Comment comment1 = new Comment(commenter, "Other Title", "other content", loc);
		
		assertFalse("different comments are not equal", comment0.equals(comment1));
	}
	
}
