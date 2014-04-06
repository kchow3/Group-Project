package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.GeoLocationController;

public class geoLocationControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public geoLocationControllerTest() {
		super(MainActivity.class);
	}
		
		protected void setUp() throws Exception {
			 super.setUp();
		}
		
		public void geoCoordinatesTest() {
			//test if phone GPS coordinates are properly read by the app
			//test if disabling the phone's GPS service affects app performance
		}
		
		@Override
		protected void tearDown() throws Exception {
		    super.tearDown();
		}

}
