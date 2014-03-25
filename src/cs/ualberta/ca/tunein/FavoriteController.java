package cs.ualberta.ca.tunein;

import android.content.Context;
import android.widget.Toast;

public class FavoriteController {

	private Comment comment;
	
	public FavoriteController(Comment aComment)
	{
		this.comment = aComment;
	}
	
	/**
	 * Method of adding comment to favorites.
	 */
	public void addtoFav(Context cntxt) {
		Favorites favs = Favorites.getInstance();
		if(!(favs.favoriteIDs.contains(comment.getElasticID())))
		{
			favs.favoriteIDs.add(comment.getElasticID());
			favs.favorites.add(comment);
			comment.increaseFavCount();
			
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
	
	public void removeFromFav(Context cntxt)
	{
		Favorites favs = Favorites.getInstance();
		if((favs.favoriteIDs.contains(comment.getElasticID())))
		{
			favs.favoriteIDs.remove(comment.getElasticID());
			favs.favorites.remove(comment);
			comment.decreaseFavCount();
			
			CharSequence text = "Removed Favorite";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(cntxt, text, duration);
			toast.show();
		}
	}
	
	public void updateFavorite()
	{
		Favorites favs = Favorites.getInstance();
		if((favs.favoriteIDs.contains(comment.getElasticID())))
		{
			favs.favorites.remove();
		}
	}
	
}
