package cs.ualberta.ca.tunein;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

/**
 * Controller
 * CommentController class:
 * This is a controller class that manipulates the comment model. The main purpose
 * of this class is when a view needs to modify anything that is related to a model.
 * To use this controller create a new CommentController object with a comment
 * and using that controller modify the comment sent to the controller.
 */
public class CommentController{

	private Comment comment;
	private ReplyViewAdapter viewAdapter;
	
	/**
	 * Constructor to construct a controller that can modify comments.
	 * @param aComment The comment that the controller modifies.
	 */
	public CommentController(Comment aComment) 
	{
		this.comment = aComment;
	}
	
	public CommentController(Comment aComment, ReplyViewAdapter adap) 
	{
		this.comment = aComment;
		this.viewAdapter = adap;
	}

	/**
	 * Method to edit the text of a comment.
	 * @param text The edited text.
	 */
	public void editText(String text) {
		comment.setComment(text);
	}

	/**
	 * Method to change the location of a comment.
	 * @param lon The longitude.
	 * @param lat The latitude.
	 */
	public void changeLoc(Double lon, Double lat) {
		comment.getGeolocation().setLongitude(lon);
		comment.getGeolocation().setLatitude(lat);
	}

	/**
	 * Method to add an image to comment.
	 * @param img The image. to be added.
	 */
	public void addImg(Bitmap bmp) {
		Image img = new Image(bmp);
		comment.setImg(img);
	}
	
	/**
	 * Method to create a reply to a comment with image.
	 * @param currentComment The topic comment that the reply will belong to.
	 * @param act The activity that calls this method.
	 * @param title The title of the comment.
	 * @param text The text of the comment.
	 * @param img The image of the comment.
	 * @param isReply Check if the added comment will be reply of reply.
	 */
	public void addReplyImg(String parentID, Context cntxt, String title, String text, Bitmap bmp, boolean isReply) {
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(cntxt);
    	String id = userCntrl.loadUserid(cntxt);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(cntxt);
		
		Image img = new Image(bmp);
		
		Comment aComment = new Comment(user, title, text, loc, img, parentID);
		comment.addReply(aComment);
		comment.increaseReplyCount();
		
		if(isReply)
		{
			aComment.increaseReplyCount();
		}
		
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);
	}
	
	
	/**
	 * Method to create a reply to a comment with image.
	 * @param currentComment The topic comment that the reply will belong to.
	 * @param act The activity that calls this method.
	 * @param title The title of the comment.
	 * @param text The text of the comment.
	 * @param isReply Check if the added comment will be reply of reply.
	 */
	public void addReply(String parentID, Context cntxt, String title, String text, boolean isReply) {
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(cntxt);
    	String id = userCntrl.loadUserid(cntxt);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation();
		GeoLocationController geoCntrl = new GeoLocationController(loc);
		geoCntrl.getLocation(cntxt);
		
		Comment aComment = new Comment(user, title, text, loc, parentID);
		comment.addReply(aComment);
		comment.increaseReplyCount();
		
		if(isReply)
		{
			aComment.increaseReplyCount();
		}
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.postCommentModel(aComment);
	}

	/**
	 * Method of adding comment to cache.
	 */
	public void addtoCache() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Method of adding comment to favorites.
	 * @param aComment The comment to be added.
	 */
	public void favorite(Comment aComment) {
		comment.increaseFavCount();
	}

	/**
	 * Method of editing a comments title.
	 * @param text The new title.
	 */
	public void editTitle(String text) {
		comment.setTitle(text);
	}
	
	/**
	 * Method to check if the current user is the comment author
	 * this is used to check credentials.
	 * @param act Activity that this method is called from.
	 * @return The resulting boolean of the check.
	 */
	public boolean checkValid(Context cntxt) {
		//id of the current viewer
		User aUser = new User();
		String currentID = aUser.getUniqueID(cntxt);
		return comment.getCommenter().getUniqueID().equals(currentID);
	}
	
	/**
	 * Method to update elastic search by pushing the
	 * comment to elastic search.
	 */
	public void updateElasticSearch()
	{
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.putCommentModel(comment);
	}
	
	/**
	 * This method goes through elastic search and gets the passed in
	 * comment's replies and also their replies.
	 * @param act
	 * @param aComment
	 */
	public void loadCommentReplies(Context cntxt)
	{
		ElasticSearchOperations eso = new ElasticSearchOperations();
		eso.getRepliesByParentId(comment.getElasticID(), comment, cntxt,viewAdapter);
	}
}
