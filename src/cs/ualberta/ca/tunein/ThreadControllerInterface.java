package cs.ualberta.ca.tunein;

import android.view.View;

public interface ThreadControllerInterface {
	
	public void sortByLocation(GeoLocation loc);
	
	public void sortBySetLocation(GeoLocation loc);
	
	public void sortByPicture();
	
	public void sortByDate();
	
}
