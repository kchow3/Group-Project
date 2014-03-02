package cs.ualberta.ca.tunein;

public interface ThreadControllerInterface {
	
	public void addTopComment(Comment aComment);
	
	public void sortByLocation(GeoLocation loc);
	
	public void sortBySetLocation(GeoLocation loc);
	
	public void sortByPicture();
	
	public void sortByDate();
}
