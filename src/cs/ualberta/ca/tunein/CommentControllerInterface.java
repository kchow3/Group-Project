package cs.ualberta.ca.tunein;

public interface CommentControllerInterface {

	public void favorite(Comment aComment);
	
	public void editText(String text);
	
	public void changeLoc(GeoLocation loc);
	
	public void addImg(Image img);
	
	public void addtoCache(Comment aComment);
	
	public void addReply(Comment aComment);
	
}
