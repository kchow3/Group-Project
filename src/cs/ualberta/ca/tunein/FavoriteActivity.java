package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * View
 * FavoriteActivity Class:
 * This class is part of the view of displaying
 * favorites by using the comment view row.
 */
public class FavoriteActivity extends Activity {

	//comment view adapter
	private CommentViewAdapter viewAdapter;
	private Favorites favs;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.favorites_view);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	    favs = Favorites.getInstance();
	    
	    viewAdapter = new CommentViewAdapter(FavoriteActivity.this, favs.favorites);
	    ListView listview = (ListView) findViewById(R.id.listViewFavorites);
		listview.setAdapter(viewAdapter);
		
		viewAdapter.updateThreadView(favs.favorites);
	}
	

}
