package cs.ualberta.ca.tunein;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * View
 * MainActivity Class:
 * This is part of the view for the main title page of the app.
 * It contains setting a username for the user and setting a sort
 * option for the comments in the app.
 * User id code from:
 * http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
 * Intent code from:
 * http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android
 */
public class MainActivity extends Activity {
	
	private TextView title;
	
	Button name_button;
	Button otherLocation_button;
	Button myLocation_button;
	Button date_button;
	Button pictures_button;
	Button buttonTopicList;
	Button fav_button;
	Button buttonCache;
	
	TextView edit_username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupPage();
		
		//setup an unique id for the user that is attached to the phone
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = "" + tm.getDeviceId();
		String androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		String id = deviceId + androidId;
		
		UserController cntrl = new UserController();
		cntrl.saveUserid(id, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Method to setup the main activity page.
	 * Font code from:
	 * http://stackoverflow.com/questions/9327053/using-custom-font-in-android-textview-using-xml
	 */
	private void setupPage()
	{
		name_button = (Button) findViewById(R.id.name_button);
		otherLocation_button = (Button) findViewById(R.id.otherLocation_button);
		myLocation_button = (Button) findViewById(R.id.myLocation_button);
		date_button = (Button) findViewById(R.id.date_button);
		pictures_button = (Button) findViewById(R.id.pictures_button);
		buttonTopicList = (Button) findViewById(R.id.buttonTopicList);
		fav_button = (Button) findViewById(R.id.fav_button);
		buttonCache = (Button) findViewById(R.id.buttonCache);
		
		edit_username = (TextView) findViewById(R.id.edit_username);
		
		UserController cntrl = new UserController();
		edit_username.setText(cntrl.loadUsername(this));
		
		name_button.setOnClickListener(renameBtnClick);
		otherLocation_button.setOnClickListener(otherLocationBtnClick);
		myLocation_button.setOnClickListener(myLocationBtnClick);
		date_button.setOnClickListener(date_buttonBtnClick);
		pictures_button.setOnClickListener(pictures_buttonBtnClick);
		buttonTopicList.setOnClickListener(topicListBtnClick);
		fav_button.setOnClickListener(favBtnClick);
		buttonCache.setOnClickListener(cacheBtnClick);

		// Set Custom fonts
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"Fonts/Action-Man/Action_Man.ttf");

		title = (TextView) findViewById(R.id.title);
		title.setTypeface(tf);
	}
	
	/**
	 * This click listener will rename the user.
	 * toast code from:
	 * http://developer.android.com/guide/topics/ui/notifiers/toasts.html
	 */
	private OnClickListener renameBtnClick = new OnClickListener() {
		public void onClick(View v) {
			UserController cntrl = new UserController();
			cntrl.changeUsername(edit_username.getText().toString(), MainActivity.this);
			
			//toast massage to confirm username change
			Context context = getApplicationContext();
			CharSequence text = "Username changed.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by other location.
	 */
	private OnClickListener otherLocationBtnClick = new OnClickListener() {
		public void onClick(View v) {
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by user location.
	 */
	private OnClickListener myLocationBtnClick = new OnClickListener() {
		public void onClick(View v) {
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by date.
	 */
	private OnClickListener date_buttonBtnClick = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),
					TopicListActivity.class);
			setSort("Date");
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by pictures.
	 */
	private OnClickListener pictures_buttonBtnClick = new OnClickListener() {
		public void onClick(View v) {
		}
	};
	
	/**
	 * This click listener will go to the favorite page.
	 */
	private OnClickListener favBtnClick = new OnClickListener() {
		public void onClick(View v) {
		}
	};
	
	/**
	 * This click listener will go to the cache page.
	 */
	private OnClickListener cacheBtnClick = new OnClickListener() {
		public void onClick(View v) {
		}
	};
	
	/**
	 * This click listener will go to the topic page sorted based on freshness(replies).
	 */
	private OnClickListener topicListBtnClick = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),
					TopicListActivity.class);
			setSort("Freshness");
			MainActivity.this.startActivity(i);
		}
	};
	
	private void setSort(String sort)
	{
		SharedPreferences prefs = this.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		prefs.edit().putString("cs.ualberta.ca.tunein.sort", sort).commit();
	}

}
