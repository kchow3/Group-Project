package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.ThreadList;

public class threadListTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public threadListTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		 super.setUp();
	}
	
	public void commentSaveTest() {
		//test if a list comments is saved to the right thread
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

}