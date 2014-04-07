package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.Image;

public class imageTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public imageTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void imageMatchTest() {
		//test if displayed image matches uploaded image after serialization and deserialization
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}