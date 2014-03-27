package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;

public class FavoriteActivity extends Activity {

	private FavoriteController favoriteController;
	//comment view adapter
	private CommentViewAdapter viewAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.favorites_view);
	    favoriteController = new FavoriteController();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		favoriteController.loadFav(getApplicationContext());
	}
	

}
