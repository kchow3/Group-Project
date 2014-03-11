package cs.ualberta.ca.tunein;


/**
 * Controller
 * CommentController class:
 * This is a controller class that manipulates the comment model. The main purpose
 * of this class is when a view needs to modify anything that is related to a model.
 * To use this controller create a new CommentController object with a comment
 * and using that controller modify the comment sent to the controller.
 */
public class CommentController implements UserControllerInterface {

	private Comment comment;
	
	/**
	 * Constructor to construct a controller that can modify comments.
	 * @param aComment The comment that the controller modifies.
	 */
	public CommentController(Comment aComment) 
	{
		this.comment = aComment;
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#editText(java.lang.String)
	 */
	@Override
	public void editText(String text) {
		comment.setComment(text);
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#changeLoc(cs.ualberta.ca.tunein.GeoLocation)
	 */
	@Override
	public void changeLoc(GeoLocation loc) {
		comment.setGeolocation(loc);
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#addImg(cs.ualberta.ca.tunein.Image)
	 */
	@Override
	public void addImg(Image img) {
		comment.setImg(img);
	}
	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#addReply(cs.ualberta.ca.tunein.Comment)
	 */
	@Override
	public void addReply(Comment aComment) {
		comment.addReply(aComment);
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#addtoCache(cs.ualberta.ca.tunein.Comment)
	 */
	@Override
	public void addtoCache(Comment aComment) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#favorite(cs.ualberta.ca.tunein.Comment)
	 */
	@Override
	public void favorite(Comment aComment) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.CommentControllerInterface#editTitle(java.lang.String)
	 */
	@Override
	public void editTitle(String text) {
		comment.setTitle(text);
	}

}
