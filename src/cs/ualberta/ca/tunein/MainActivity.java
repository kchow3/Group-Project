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
import android.widget.ImageButton;
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
	
	private MainActivityProduct mainActivityProduct = new MainActivityProduct();
	//public string that are used to get the sort info
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
	private ImageButton imageButtonRefreshLoc;
	
	private TextView edit_username;
	
	private GeoLocationController geoController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupPage();
				
		//load in user unique id
		UserController cntrl = new UserController();
		cntrl.saveUserid(getApplicationContext());
		cntrl.createProfile(MainActivity.this);
		
		//load in the favorites
		FavoriteController favoriteController = new FavoriteController();
		favoriteController.loadFav(getApplicationContext());
		
		//load in the cache
		CacheController cacheController = new CacheController();
		cacheController.loadCache(getApplicationContext());
		
		mainActivityProduct.setLoc(new GeoLocation());
		geoController = new GeoLocationController(mainActivityProduct.getLoc());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		geoController.getLocation(MainActivity.this);
		mainActivityProduct.loadLoc();
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
		imageButtonRefreshLoc = (ImageButton) findViewById(R.id.imageButtonRefreshLoc);
		
		edit_username = (TextView) findViewById(R.id.edit_username);
		mainActivityProduct
				.setLocation_text((TextView) findViewById(R.id.location_text));
		
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
		imageButtonRefreshLoc.setOnClickListener(refreshLocBtnClick);
		
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
	    	mainActivityProduct.setupDialogs(MainActivity.this);
			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
			    .setTitle("Set Location")
			    .setView(mainActivityProduct.getCreateView())
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			    		mainActivityProduct.setSort("Set Location", MainActivity.this);
			    		mainActivityProduct.setLoc(false, MainActivity.this);
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
			mainActivityProduct.setSort("My Location", MainActivity.this);
			mainActivityProduct.setLoc(true, MainActivity.this);
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
			mainActivityProduct.setSort("Date", MainActivity.this);
			MainActivity.this.startActivity(i);
		}
	};
	
	/**
	 * This click listener will go topic list page and sort by pictures.
	 */
	private OnClickListener pictures_buttonBtnClick = new OnClickListener() {
		public void onClick(View v) {
			mainActivityProduct.setSort("Picture", MainActivity.this);
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
			mainActivityProduct.setSort("default", MainActivity.this);
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
	
	/**
	 * This click listener will refresh the location text view
	 */
	private OnClickListener refreshLocBtnClick = new OnClickListener() {
		public void onClick(View v) {
			geoController.getLocation(MainActivity.this);
			mainActivityProduct.loadLoc();
		}
	};

}
