package cs.ualberta.ca.tunein;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs.ualberta.ca.tunein.network.BitmapJsonConverter;
import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface;

/**
 * Controller
 * CacheController Class:
 * This controller is used to modify the cache list of 
 * a user. The main objective of the class is adding,
 * removing, saving and loading from the cache singleton
 * class.
 */
public class CacheController {

	public final static String SAVE_FILE = "cache.sav";

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
	
	/**
	 * This method adds a saved comment to the cache list.
	 * The max size of the list is 20 comments before the
	 * removal of the last comment (first in first out style)
	 * @param cntxt The context of the application.
	 * @param comment The comment that is saved
	 */
	public void addToCache(Context cntxt, Comment comment)
	{
		if(!(saves.cacheIDs.contains(comment.getElasticID())))
		{
			ElasticSearchOperationsInterface eso = new ElasticSearchOperations();
			//get the saved comment's replies
			eso.getReplyReplies(comment, comment.getElasticID(), cntxt);
			//add new saved comment to beginning of list
			saves.cacheIDs.add(0, comment.getElasticID());
			saves.cacheList.add(0, comment);
			//remove last element if cache gets too large
			if(saves.cacheIDs.size() > 20)
			{
				removeFromCache(cntxt);
			}
			saveCache(cntxt);
			
			CharSequence text = "Saved!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
		else
		{
			CharSequence text = "Already Saved.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
	}
	
	/**
	 * Method to remove a saved comment from the cache, 
	 * remove comment when cache gets too full.
	 * Use first in first out style of removing.
	 * @param cntxt The context of the application
	 * @param comment The comment to be removed
	 */
	public void removeFromCache(Context cntxt)
	{
		saves.cacheIDs.remove(saves.cacheIDs.size()-1);
		saves.cacheList.remove(saves.cacheList.size()-1);
	}
	
	/**
	 * Method to check if comment is currently in the cache.
	 * @param comment The comment to be checked
	 * @return The result of the check
	 */
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
	
	/**
	 * Method to save the saved comments (cache) to a file.
	 * @param cntxt The application context.
	 */
	private void saveCache(Context cntxt)
	{
		Type cacheType = new TypeToken<Cache>(){}.getType();
		String jsonString = GSON.toJson(saves, cacheType);
        try {
        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cntxt.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE));
			outputStreamWriter.write(jsonString);
	        outputStreamWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to load the saved comments (cache) from a file.
	 * @param cntxt The application context
	 */
	public void loadCache(Context cntxt)
	{
		File file = cntxt.getFileStreamPath(SAVE_FILE);
		saves.cacheIDs = new ArrayList<String>();
		saves.cacheList = new ArrayList<Comment>();
		if(file.exists())
		{
			String jsonString = "";
	
		    try {
		        InputStream inputStream = cntxt.openFileInput(SAVE_FILE);
	
		        if ( inputStream != null ) {
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String receiveString = "";
		            StringBuilder stringBuilder = new StringBuilder();
	
		            while ( (receiveString = bufferedReader.readLine()) != null ) {
		                stringBuilder.append(receiveString);
		            }
	
		            inputStream.close();
		            jsonString = stringBuilder.toString();
		        }
		        Type cacheType = new TypeToken<Favorites>(){}.getType();
		        saves.setInstance((Cache) GSON.fromJson(jsonString, cacheType));
		    }
		    catch (FileNotFoundException e) {
		        Log.e("FAV:", "File not found: " + e.toString());
		    } catch (IOException e) {
		        Log.e("FAV:", "Can not read file: " + e.toString());
		    }
		}
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
