package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.GeoLocation;

public class geoLocationTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public geoLocationTest() {
		super(MainActivity.class);
	}
		
		protected void setUp() throws Exception {
			 super.setUp();
		}
		
		public void geoInfoTest() {
			//test if a geo location consists of relevant coordinates
			//test with invalid coordinate entries
		}
		
		@Override
		protected void tearDown() throws Exception {
		    super.tearDown();
		}

}
