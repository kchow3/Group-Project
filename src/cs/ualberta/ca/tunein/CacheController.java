package cs.ualberta.ca.tunein;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cs.ualberta.ca.tunein.network.BitmapJsonConverter;

/**
 * Controller
 * CacheController Class:
 * This controller is used to modify the cache list of 
 * a user. The main objective of the class is adding,
 * removing, saving and loading from the cache singleton
 * class.
 */
public class CacheController {

	public final static String FAV_FILE = "cache.sav";

	private Cache saves;
	private Gson GSON;
	
	/**
	 * CacheController constructor that assigns
	 * the cache class to be modified
	 */
	public CacheController()
	{
		saves = Cache.getInstance();
		constructGson();
	}
	
	public void addToCache(Context cntxt, Comment comment)
	{
		
	}
	
	public void removeFromCache(Context cntxt, Comment comment)
	{
		
	}
	
	public boolean inCache(Comment comment)
	{
		if(saves.cacheIDs.contains(comment.getElasticID()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void saveCache(Context cntxt)
	{
		
	}
	
	public void loadCache(Context cntxt)
	{
		
	}
	
	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for
	 * Bitmaps.
	 */
	private void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}
}
