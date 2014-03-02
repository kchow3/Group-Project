package cs.ualberta.ca.tunein;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
	
	private Commenter commenter;
	private String title;
	private String comment;
	private ArrayList<Comment> replies;
	private Image img;
	private Date date;
	private GeoLocation geolocation;
	

	public Comment(Commenter user, String aTitle, String aComment, Image aImage, GeoLocation loc) 
	{
		
		this.commenter = user;
		this.title = aTitle;
		this.comment = aComment;
		this.img = aImage;
		this.date = new Date();
		this.geolocation = loc;
	}

}
