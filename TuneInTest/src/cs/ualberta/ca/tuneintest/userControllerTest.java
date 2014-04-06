package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.UserController;

public class userControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public userControllerTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void usernameChangeTest() {
		//test if username is changed on all views after changing the username from the title screen
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}