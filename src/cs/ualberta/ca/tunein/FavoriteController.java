package cs.ualberta.ca.tunein;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs.ualberta.ca.tunein.network.BitmapJsonConverter;
import cs.ualberta.ca.tunein.network.ElasticSearchResponse;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

/**
 * Controller
 * FavoriteController Class:
 * This class is used to modify the favorites list of a 
 * user. The main objective of the class is adding and
 * removing from the favorites singleton class. 
 * Also to save/load the favorites from a file.
 */
public class FavoriteController {
	
	public final static String FAV_FILE = "favorites.sav";

	private Favorites favs;
	private Gson GSON;
	
	
	/**
	 * FavoriteController constructor that is mainly
	 * used to load favorites.
	 */
	public FavoriteController()
	{
		favs = Favorites.getInstance();
		constructGson();
	}
	
	/**
	 * Method of adding comment to favorites.
	 * @param cntxt The context of the app to show toast.
	 */
	public void addtoFav(Context cntxt, Comment comment) {
		if(!(favs.favoriteIDs.contains(comment.getElasticID())))
		{
			//add new favorite to beginning of list
			favs.favoriteIDs.add(0, comment.getElasticID());
			favs.favorites.add(0, comment);
			comment.increaseFavCount();
			saveFav(cntxt);
			
			CharSequence text = "Favorited!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
		else
		{
			CharSequence text = "Already Favorited.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
	}
	
	/**
	 * Method to remove a comment from favorites.
	 * @param cntxt The context of the app to show toast.
	 */
	public void removeFromFav(Context cntxt, Comment comment)
	{
		if((favs.favoriteIDs.contains(comment.getElasticID())))
		{
			favs.favoriteIDs.remove(comment.getElasticID());
			favs.favorites.remove(comment);
			comment.decreaseFavCount();
			saveFav(cntxt);
			
			CharSequence text = "Removed Favorite";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
	}
	
	/**
	 * Method to see if current comment is in favorites.
	 * @return Whether comment is in favorites.
	 */
	public boolean inFav(Comment comment)
	{
		if(favs.favoriteIDs.contains(comment.getElasticID()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method to save the favorites to file.
	 * Code from:
	 * http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
	 */
	private void saveFav(Context cntxt)
	{
		Type favoriteType = new TypeToken<Favorites>(){}.getType();
		String jsonString = GSON.toJson(favs, favoriteType);
	
        try {
        	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cntxt.openFileOutput(FAV_FILE, Context.MODE_PRIVATE));
			outputStreamWriter.write(jsonString);
	        outputStreamWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to load the saved favorites to the singleton 
	 * favorite class.
	 * Code from:
	 * http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
	 */
	public void loadFav(Context cntxt)
	{
		File file = cntxt.getFileStreamPath(FAV_FILE);
		favs.favoriteIDs = new ArrayList<String>();
		favs.favorites = new ArrayList<Comment>();
		if(file.exists())
		{
			String jsonString = "";
	
		    try {
		        InputStream inputStream = cntxt.openFileInput(FAV_FILE);
	
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
		        Type favoriteType = new TypeToken<Favorites>(){}.getType();
		        favs = GSON.fromJson(jsonString, favoriteType);
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
