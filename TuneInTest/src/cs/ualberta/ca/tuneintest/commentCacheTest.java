package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CommentCache;

public class commentCacheTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentCacheTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void cachedListTest() {
		//test that desired comments are saved in this array list
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}
