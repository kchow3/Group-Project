package cs.ualberta.ca.tunein;

public interface ThreadControllerInterface {
	
	public void addTopComment(Commenter user, String aTitle, String aComment, GeoLocation loc);
	
	public void addTopComment(Commenter user, String aTitle, String aComment, GeoLocation loc, Image img);
	
	public void sortByLocation(GeoLocation loc);
	
	public void sortBySetLocation(GeoLocation loc);
	
	public void sortByPicture();
	
	public void sortByDate();
}
