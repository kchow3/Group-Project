package cs.ualberta.ca.tunein;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

/**
 * View
 * MainActivity Class:
 * This is part of the view for the main title page of the app.
 * It contains setting a username for the user and setting a sort
 * option for the comments in the app.
 */
public class MainActivity extends Activity {
	
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Set Custom fonts
		Typeface tf = Typeface.createFromAsset(getAssets(), "Fonts/Action-Man/Action_Man.ttf");
		
		title = (TextView) findViewById(R.id.title);
		title.setTypeface(tf);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
