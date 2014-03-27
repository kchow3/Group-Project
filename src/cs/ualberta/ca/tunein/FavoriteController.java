package cs.ualberta.ca.tunein;

import android.content.Context;
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

	private Comment comment;
	private Favorites favs;
	
	/**
	 * FavoriteController constructor that takes
	 * the passed in comment and modify the favorite
	 * list.
	 * @param aComment Comment to modify favorites.
	 */
	public FavoriteController(Comment aComment)
	{
		this.comment = aComment;
		Favorites favs = Favorites.getInstance();
	}
	/**
	 * Method of adding comment to favorites.
	 * @param cntxt The context of the app to show toast.
	 */
	public void addtoFav(Context cntxt) {
		if(!(favs.favoriteIDs.contains(comment.getElasticID())))
		{
			//add new favorite to beginning of list
			favs.favoriteIDs.add(0, comment.getElasticID());
			favs.favorites.add(0, comment);
			comment.increaseFavCount();
			saveFav();
			
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
	public void removeFromFav(Context cntxt)
	{
		if((favs.favoriteIDs.contains(comment.getElasticID())))
		{
			favs.favoriteIDs.remove(comment.getElasticID());
			favs.favorites.remove(comment);
			comment.decreaseFavCount();
			saveFav();
			
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
	public boolean inFav()
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
	 */
	private void saveFav()
	{
		
	}
	
	/**
	 * Method to load the saved favorites to the singleton 
	 * favorite class.
	 */
	public void loadFav()
	{
		
	}
	
}
