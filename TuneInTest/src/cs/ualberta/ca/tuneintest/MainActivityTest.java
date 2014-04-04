package cs.ualberta.ca.tuneintest;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import cs.ualberta.ca.tunein.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	Activity activity;
	EditText textInput;
	Instrumentation instrumentation;
	
	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		instrumentation = getInstrumentation();
		activity = getActivity();

		textInput = ((EditText) activity.findViewById(cs.ualberta.ca.tunein.R.id.edit_username));
	}
	
	
	public void testdatesortbutton() {
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.TopicListActivity.class.getName(), null, false);
		
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.date_button);
		TouchUtils.clickView(this, button);
		
		cs.ualberta.ca.tunein.TopicListActivity secondActivity = (cs.ualberta.ca.tunein.TopicListActivity) monitor
		          .waitForActivityWithTimeout(5);
		assertNotNull(secondActivity);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
	
	public void testtopicbutton() {
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(cs.ualberta.ca.tunein.TopicListActivity.class.getName(), null, false);
		
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonTopicList);
		TouchUtils.clickView(this, button);
		
		cs.ualberta.ca.tunein.TopicListActivity secondActivity = (cs.ualberta.ca.tunein.TopicListActivity) monitor
		          .waitForActivityWithTimeout(5);
		assertNotNull(secondActivity);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}
	
	public void testchangename()throws Throwable{
		runTestOnUiThread(new Runnable(){
			
			@Override
			public void run(){
			
				// TODO Auto-generated method stub
				setname("Changed Name");
			}
		});
		
	}
	
	public void setname(String name){
		assertNotNull(activity.findViewById(cs.ualberta.ca.tunein.R.id.name_button));
		textInput.setText(name);
		((Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.name_button)).performClick();	
	}
	

}
