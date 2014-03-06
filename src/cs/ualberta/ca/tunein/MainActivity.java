package cs.ualberta.ca.tunein;

import android.os.Bundle;
import android.app.Activity;
<<<<<<< HEAD
import android.view.Menu;

public class MainActivity extends Activity {
=======
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView title;
>>>>>>> upstream/master

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
<<<<<<< HEAD
=======
		
		//Set Custom fonts
		Typeface tf = Typeface.createFromAsset(getAssets(), "Fonts/Action-Man/Action_Man.ttf");
		
		title = (TextView) findViewById(R.id.title);
		title.setTypeface(tf);
		
>>>>>>> upstream/master
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
