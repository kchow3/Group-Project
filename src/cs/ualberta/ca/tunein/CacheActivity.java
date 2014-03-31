package cs.ualberta.ca.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * View
 * CacheActivity Class:
 * This part of the view of displaying saved comments
 * that are saved to the cache. This is done through
 * the use of the comment view row.
 *
 */
public class CacheActivity extends Activity {

	//comment view adapter
	private CommentViewAdapter viewAdapter;
	private Cache cache;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.cache_view);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	    cache = Cache.getInstance();
	    
	    viewAdapter = new CommentViewAdapter(CacheActivity.this, cache.cacheList);
	    ListView listview = (ListView) findViewById(R.id.listViewCache);
		listview.setAdapter(viewAdapter);
		
		viewAdapter.updateThreadView(cache.cacheList);
	}

}
