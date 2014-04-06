package cs.ualberta.ca.tuneintest;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.ThreadController;

public class threadControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public threadControllerTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void topicRetreivalTest() {
		//test if topic comments can be retrieved from ElasticSearch
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}
