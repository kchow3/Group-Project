package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.FavoriteController;

public class favoriteControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public favoriteControllerTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void addFavTest() {
		//test if comments are added to the favorites list
	}
	
	public void removeFavTest() {
		//test if comments can be removed from the favorites list
	}
	
	public void containsFavTest() {
		//test if specified comments are contained in the favorites list
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}