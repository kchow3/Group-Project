package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FavoriteActivity extends Activity {

	private FavoriteController favoriteController;
	//comment view adapter
	private CommentViewAdapter viewAdapter;
	private Favorites favs;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.favorites_view);
	    favs = Favorites.getInstance();
	    favoriteController = new FavoriteController();
	    viewAdapter = new CommentViewAdapter(FavoriteActivity.this, favs.favorites);
	    ListView listview = (ListView) findViewById(R.id.listViewFavorites);
		listview.setAdapter(viewAdapter);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		favoriteController.loadFav(getApplicationContext());
		viewAdapter.updateThreadView(favs.favorites);
	}
	

}
