package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CacheController;

public class cacheControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public cacheControllerTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}

	public void addToCacheTest() {
		//test if comments are successfully added to the cache
	}
	
	public void removeFromCacheTest() {
		//test if comments can be successfully removed from the cache
	}
	
	public void cacheSizeTest() {
		//test if comments are removed properly upon reaching max amount of caches comments
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
}