package cs.ualberta.ca.tunein;

public class CommentController implements CommentControllerInterface {

	private Comment comment;
	
	public CommentController(Comment aComment) 
	{
		this.comment = aComment;
	}

	@Override
	public void editText(String text) 
	{
		comment.setComment(text);
	}

	@Override
	public void changeLoc(GeoLocation loc) 
	{
		comment.setGeolocation(loc);
	}

	@Override
	public void addImg(Image img) {
		comment.setImg(img);
	}
	
	@Override
	public void addReply(Comment aComment) {
		comment.addReply(aComment);
	}

	@Override
	public void addtoCache(Comment aComment) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void favorite(Comment aComment) 
	{
		// TODO Auto-generated method stub
	}

}
