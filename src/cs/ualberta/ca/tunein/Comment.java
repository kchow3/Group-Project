package cs.ualberta.ca.tunein;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

/**
 * Model
 * Comment Class:
 * This is class is part of the comment model. Its major function is the attributes
 * of a comment. The most important attributes of a comment is the title, the comment
 * text, replies, geo location, and image. This references commenter, geo location and
 * image classes. This class is used in the comment controller.
 *
 */
public class Comment implements Serializable
{
	//user that creates the comment
	private Commenter commenter;
	//comment title
	private String title;
	//comment body
	private String comment;
	//replies to the comment
	private ArrayList<Comment> replies;
	//iamge of comment
	private Image img;
	//date of comment
	private String date;
	//geo location of comment
	private GeoLocation geolocation;
	//does comment have an image
	private boolean hasImage;
	//number of times this comment has been favorited
	private int favoriteCount;
	//number of replies to the comment
	private int replyCount;
	//id of Elastic Search
	private String elasticID;
	//parent elastic id
	private String parentID;
	
	/**
	 * This constructor constructs a comment without an image.
	 * @param user The user that comment belongs to.
	 * @param aTitle The title of the comment.
	 * @param aComment The comment body.
	 * @param loc The geo location of comment.
	 */
	public Comment(Commenter user, String aTitle, String aComment, GeoLocation loc, String parent) 
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		this.commenter = user;
		this.title = aTitle;
		this.comment = aComment;
		this.geolocation = loc;
		this.date = df.format(new Date());
		this.replies = new ArrayList<Comment>();
		this.hasImage = false;
		this.favoriteCount = 0;
		this.replyCount = 0;
		this.elasticID = null;
		this.parentID = parent;
	}

	/**
	 * This constructor constructs a comment with an image.
	 * @param user The user that comment belongs to.
	 * @param aTitle The title of the comment.
	 * @param aComment The comment body.
	 * @param loc The geo location of comment.
	 * @param aImage The image of comment.
	 */
	public Comment(Commenter user, String aTitle, String aComment, GeoLocation loc, Image aImage, String parent) 
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		this.commenter = user;
		this.title = aTitle;
		this.comment = aComment;
		this.geolocation = loc;
		this.img = aImage;
		this.date = df.format(new Date());
		this.replies = new ArrayList<Comment>();
		this.hasImage = true;
		this.favoriteCount = 0;
		this.replyCount = 0;
		this.elasticID = null;
		this.parentID = parent;
	}

	public Commenter getCommenter() {
		return commenter;
	}

	public void setCommenter(Commenter commenter) {
		this.commenter = commenter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ArrayList<Comment> getReplies() {
		return replies;
	}

	public void setReplies(ArrayList<Comment> replies) {
		this.replies = replies;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
		this.hasImage = true;
	}

	public String getDate() {
		return date;
	}

	public void setDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.date = df.format(date);
	}

	public GeoLocation getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(GeoLocation geolocation) {
		this.geolocation = geolocation;
	}
	
	public void addReply(Comment aComment){
		this.replies.add(aComment);
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	public String getElasticID() {
		return elasticID;
	}

	public void setElasticID(String elasticID) {
		this.elasticID = elasticID;
	}
	

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	/**
	 * Increment the number times the comment was favorited.
	 */
	public void increaseFavCount()
	{
		this.favoriteCount++;
	}
	
	/**
	 * Decrement the number times the comment was favorited.
	 */
	public void decreaseFavCount()
	{
		this.favoriteCount--;
	}
	
	/**
	 * Increase the number of times the comment has been replied to.
	 */
	public void increaseReplyCount()
	{
		this.replyCount++;
	}

	public void setupComment(Comment source) {
		this.commenter = source.getCommenter();
		this.title = source.getTitle();
		this.comment = source.getComment();
		this.geolocation = source.getGeolocation();
		this.date = source.getDate();
		this.replies = source.getReplies();
		this.hasImage = source.isHasImage();
		this.favoriteCount = source.getFavoriteCount();
		this.replyCount = source.getReplyCount();
		this.elasticID = source.getElasticID();
		this.parentID = source.getParentID();
		
	}
	
	public void addReplies(ArrayList<Comment> replies)
	{
		this.replies.addAll(replies);
		this.replyCount = this.replies.size();
		Log.v("param reply size:", Integer.toString(this.replies.size()));
		Log.v("this reply size:", Integer.toString(replies.size()));
	}
	
	public void clear() {
		this.replies.clear();
	}
	
	/**
	 * Method to convert the date to a format for better viewing
	 * @return The date.
	 */
	public String dateDisplay()
	{
		SimpleDateFormat old_df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat new_df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();;
		try {
			date = (Date)old_df.parse(this.date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new_df.format(date);
	}

}
