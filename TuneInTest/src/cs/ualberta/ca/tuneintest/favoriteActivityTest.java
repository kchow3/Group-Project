package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.FavoriteActivity;

public class favoriteActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public favoriteActivityTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void favoritesViewTest() {
		//test if favorites are displayed
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}