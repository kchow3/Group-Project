package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.Favorites;

public class favoritesTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public favoritesTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void favoritesInstanceTest() {
		//test if the desired comment data has been saved in this array list
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}