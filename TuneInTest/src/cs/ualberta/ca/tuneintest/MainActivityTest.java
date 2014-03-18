package cs.ualberta.ca.tuneintest;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	Activity activity;
	
	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	public void testdatesortbutton() {
		activity = getActivity();
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.TopicListActivity.class.getName(), null, false);
		
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.date_button);
		TouchUtils.clickView(this, button);
		
		cs.ualberta.ca.tunein.TopicListActivity secondActivity = (cs.ualberta.ca.tunein.TopicListActivity) monitor
		          .waitForActivityWithTimeout(5);
		assertNotNull(secondActivity);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
	

}
