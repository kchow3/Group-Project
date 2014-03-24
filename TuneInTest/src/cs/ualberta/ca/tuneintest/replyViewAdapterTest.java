package cs.ualberta.ca.tuneintest;

import java.util.Date;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import cs.ualberta.ca.tunein.CommentPageActivity;
import cs.ualberta.ca.tunein.TopicListActivity;

public class replyViewAdapterTest extends ActivityInstrumentationTestCase2<CommentPageActivity> {
	
	Activity activity;
	
	public replyViewAdapterTest() {
		super(CommentPageActivity.class);
	}
	
	/**public void testReplyOfReplyButton() {
		activity = getActivity();
		Button button = (Button) activity.findViewById(cs.ualberta.ca.tunein.R.id.buttonReply);
		TouchUtils.clickView(this, button);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
	}**/
	
//Need to add tests for all properties of the ReplyViewAdapter class

}