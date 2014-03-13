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
		Commenter commenter = new Commenter("Anonymous", "ABC");
		GeoLocation loc = new GeoLocation(0, 0);
		
		Comment comment0 = new Comment(commenter , "Sample Title", "sample comment", loc);
		Comment comment1 = new Comment(commenter, "Other Title", "other content", loc);
		
		assertFalse("different comments are not equal", comment0.equals(comment1));
	}
	
//Need to add tests for all properties of the Comment class
//Need to add the following tests: commenter's name displays properly
//comment title displays properly
//replies display properly
//images can be seen and added to comments
//date information is displayed properly
//geo location info can be seen and is accurate
//fav and save info can be seen
//reply count is displayed properly


}
