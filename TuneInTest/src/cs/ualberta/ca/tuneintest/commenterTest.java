package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.Commenter;

public class commenterTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commenterTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void createCommentTest() {
		//test if comment data matches the format of a comment
		//test for empty comment entries
	}

	public void setupProfileTest() {
		//test if name and ID matches from commenter data
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
}