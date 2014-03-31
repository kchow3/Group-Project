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
	 * @param cntxt The context of the application.
	 * @param comment The comment that is saved
	 */
	public void addToCache(Context cntxt, Comment comment)
	{
		if(!(saves.cacheIDs.contains(comment.getElasticID())))
		{

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
	 * remove comment when cache gets too full
	 * @param cntxt The context of the application
	 * @param comment The comment to be removed
	 */
	public void removeFromCache(Context cntxt, Comment comment)
	{
		if((saves.cacheIDs.contains(comment.getElasticID())))
		{
			
			CharSequence text = "Removed Saved Comment";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
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
