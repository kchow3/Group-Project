package cs.ualberta.ca.tunein;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Controller
 * CommentController class:
 * This is a controller class that manipulates the comment model. The main purpose
 * of this class is when a view needs to modify anything that is related to a model.
 * To use this controller create a new CommentController object with a comment
 * and using that controller modify the comment sent to the controller.
 */
public class CommentController implements CommentControllerInterface {

	private Comment comment;
	
	/**
	 * Constructor to construct a controller that can modify comments.
	 * @param aComment The comment that the controller modifies.
	 */
	public CommentController(Comment aComment) 
	{
		this.comment = aComment;
	}

	@Override
	public void editText(String text) {
		comment.setComment(text);
	}

	@Override
	public void changeLoc(GeoLocation loc) {
		comment.setGeolocation(loc);
	}

	@Override
	public void addImg(Image img) {
		comment.setImg(img);
	}
	
	@Override
	public void addReplyImg(Comment currentComment, Activity act, String title, String text, Image img) {
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation(20, 30);
		
		Comment aComment = new Comment(user, title, text, loc, img);
		comment.addReply(aComment);
		comment.increaseReplyCount();
		
		ElasticSearchOperations.putCommentModel(currentComment);
	}
	
	@Override
	public void addReply(Comment currentComment, Activity act, String title, String text) {
		
		UserController userCntrl = new UserController();
    	String username = userCntrl.loadUsername(act);
    	String id = userCntrl.loadUserid(act);
		Commenter user = new Commenter(username, id);
		
		GeoLocation loc = new GeoLocation(20, 30);
		
		Comment aComment = new Comment(user, title, text, loc);
		comment.addReply(aComment);
		comment.increaseReplyCount();
		
		ElasticSearchOperations.putCommentModel(currentComment);
	}

	@Override
	public void addtoCache(Comment aComment) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void favorite(Comment aComment) {
		comment.increaseFavCount();
	}

	@Override
	public void editTitle(String text) {
		comment.setTitle(text);
	}
	
	@Override
	public boolean checkValid(Activity act) {
		//id of the current viewer
		SharedPreferences prefs = act.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		String currentID = prefs.getString("cs.ualberta.ca.tunein.userid", "");
		return comment.getCommenter().getUniqueID().equals(currentID);
	}
}
