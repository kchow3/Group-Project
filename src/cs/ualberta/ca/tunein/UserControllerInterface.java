package cs.ualberta.ca.tunein;

/**
 * Controller
 * CommentControllerInterface Interface:
 * This interface is for implementing a CommentController.
 */
public interface UserControllerInterface {

	/**
	 * This method is used to favorite a comment.
	 * @param aComment The comment that is favorited.
	 */
	public void favorite(Comment aComment);
	
	/**
	 * This method edits the comment body of a comment.
	 * @param text The new text that will replace the old text.
	 */
	public void editText(String text);
	
	/**
	 * This method changes the geolocation of the comment.
	 * @param loc The new location that the comment will be changed to.
	 */
	public void changeLoc(GeoLocation loc);
	
	/**
	 * This method adds/changes the image of the comment.
	 * @param img The new image that will be set in the comment.
	 */
	public void addImg(Image img);
	
	/**
	 * This method adds the comment to the cache.
	 * @param aComment The comment that will be added to the cache.
	 */
	public void addtoCache(Comment aComment);
	
	/**
	 * This method adds a reply comment to the comment.
	 * @param aComment The reply of the comment.
	 */
	public void addReply(Comment aComment);
	
	/**
	 * This method changes the title of the comment.
	 * @param text The new title of the comment.
	 */
	public void editTitle(String text);
	
}
