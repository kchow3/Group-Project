package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CacheActivity;

public class cacheActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public cacheActivityTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void cacheViewTest() {
		//test if the cached comments are displayed correctly 
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
}