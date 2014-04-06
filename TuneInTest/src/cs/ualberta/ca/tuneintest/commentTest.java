package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;

public class commentTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void commentAttributesTest() {
		//test if a comment has a title, text, replies, a geo location, and an image
		//test what happens if a comment has invalid attributes
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}
