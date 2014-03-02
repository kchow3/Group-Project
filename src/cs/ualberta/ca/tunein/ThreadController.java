package cs.ualberta.ca.tunein;

import java.util.ArrayList;

public class ThreadController implements ThreadControllerInterface {

	private Thread discussionThread;
	
	public ThreadController() {
	
		discussionThread = new Thread();
	}
	

	@Override
	public void addTopComment(Commenter user, String aTitle, String aComment,
			GeoLocation loc) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void addTopComment(Commenter user, String aTitle, String aComment,
			GeoLocation loc, Image img) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void sortByLocation(GeoLocation loc) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortBySetLocation(GeoLocation loc) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortByPicture() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortByDate() 
	{
		// TODO Auto-generated method stub
		
	}

}
