package cs.ualberta.ca.tunein;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
	
	//public string that are used to get the sort info
	public final static String SORT = "cs.ualberta.ca.tunein.sort";
	public final static String SORTLONG = "cs.ualberta.ca.tunein.sortLong";
	public final static String SORTLAT = "cs.ualberta.ca.tunein.sortLat";
	
	//public string that tags the extra of the comment to be edited that is passed to EditPageActivity
	public final static String EXTRA_USERID = "cs.ualberta.ca.tunein.userid";
	
	private TextView title;
	
	private Button name_button;
	private Button otherLocation_button;
	private Button myLocation_button;
	private Button date_button;
	private Button pictures_button;
	private Button buttonTopicList;
	private Button fav_button;
	private Button buttonCache;
	private Button buttonViewProfile;;
	
	private TextView location_text;
	private TextView edit_username;
	
	//dialog elements
	private View createView;
	private TextView inputLong;
	private TextView inputLat;
	
	private GeoLocation loc;
	private GeoLocationController geoController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupPage();
				
		//load in user unique id
		UserController cntrl = new UserController();
		cntrl.saveUserid(getApplicationContext());
		
		//load in the favorites
		FavoriteController favoriteController = new FavoriteController();
		favoriteController.loadFav(getApplicationContext());
		
		//load in the cache
		CacheController cacheController = new CacheController();
		cacheController.loadCache(getApplicationContext());
		
		loc = new GeoLocation();
		geoController = new GeoLocationController(loc);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		geoController.getLocation(MainActivity.this);
		loadLoc();
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
		buttonViewProfile = (Button) findViewById(R.id.buttonViewProfile);
		
		edit_username = (TextView) findViewById(R.id.edit_username);
		location_text = (TextView) findViewById(R.id.location_text);
		
		UserController cntrl = new UserController();
		edit_username.setText(cntrl.loadUsername(getApplicationContext()));
		
		name_button.setOnClickListener(renameBtnClick);
		otherLocation_button.setOnClickListener(otherLocationBtnClick);
		myLocation_button.setOnClickListener(myLocationBtnClick);
		date_button.setOnClickListener(date_buttonBtnClick);
		pictures_button.setOnClickListener(pictures_buttonBtnClick);
		buttonTopicList.setOnClickListener(topicListBtnClick);
		fav_button.setOnClickListener(favBtnClick);
		buttonCache.setOnClickListener(cacheBtnClick);
		
		buttonViewProfile.setOnClickListener(profileBtnClick);

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
			cntrl.changeUsername(edit_username.getText().toString(), getApplicationContext());
			
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
	    	setupDialogs();
			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
			    .setTitle("Set Location")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			    		setSort("Set Location");
			    		setLoc(false);
						Intent i = new Intent(getApplicationContext(),
								TopicListActivity.class);
						MainActivity.this.startActivity(i);
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by user location.
	 */
	private OnClickListener myLocationBtnClick = new OnClickListener() {
		public void onClick(View v) {
			setSort("My Location");
			setLoc(true);
			Intent i = new Intent(getApplicationContext(),
					TopicListActivity.class);
			MainActivity.this.startActivity(i);
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
			setSort("Picture");
			Intent i = new Intent(getApplicationContext(),
					TopicListActivity.class);
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go to the favorite page.
	 */
	private OnClickListener favBtnClick = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),
					FavoriteActivity.class);
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go to the cache page.
	 */
	private OnClickListener cacheBtnClick = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),
					CacheActivity.class);
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go to the topic page sorted based on freshness(replies).
	 */
	private OnClickListener topicListBtnClick = new OnClickListener() {
		public void onClick(View v) {
			setSort("default");
			Intent i = new Intent(getApplicationContext(),
					TopicListActivity.class);
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go to the profile page.
	 */
	private OnClickListener profileBtnClick = new OnClickListener() {
		public void onClick(View v) {
			UserController cntrl = new UserController();
			String userid = cntrl.loadUserid(getApplicationContext());
			Intent i = new Intent(getApplicationContext(),
					ProfileViewActivity.class);
			i.putExtra(EXTRA_USERID, userid);
			MainActivity.this.startActivity(i);
		}
	};
	
	private void setSort(String sort)
	{
		SharedPreferences prefs = this.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		prefs.edit().putString(SORT, sort).commit();
	}
	
	/**
	 * Set the location of the sort.
	 * @param myLoc Is the sort by my location
	 */
	private void setLoc(boolean myLoc)
	{
		SharedPreferences prefs = this.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		if(myLoc)
		{
	    	prefs.edit().putString(SORTLONG, String.valueOf(loc.getLongitude())).commit();
	    	prefs.edit().putString(SORTLAT, String.valueOf(loc.getLongitude())).commit();
		}
		else
		{
	  		prefs.edit().putString(SORTLONG, inputLong.getText().toString()).commit();
	  		prefs.edit().putString(SORTLAT, inputLat.getText().toString()).commit();
		}
	}
	
	private void loadLoc()
	{
		String result = "@ " + String.valueOf(loc.getLongitude()) + ", " + String.valueOf(loc.getLatitude());
		location_text.setText(result);
	}
	
	/**
	 * Setup change location dialog box
	 */
	private void setupDialogs()
	{
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		createView = inflater.inflate(R.layout.location_change, null);

		inputLong = (EditText) createView.findViewById(R.id.textViewInputChangeLong);
		inputLat = (EditText) createView.findViewById(R.id.textViewInputChangeLat);
	}

}
