package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.ImageController;

public class imageControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public imageControllerTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}

	public void imageUploadTest() {
		//test if image is uploaded correctly
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}