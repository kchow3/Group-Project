package cs.ualberta.ca.tunein;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	private Date commentDate;
	//geo location of comment
	private GeoLocation geolocation;
	//does comment have an image
	private boolean hasImage;
	//is comment saved
	private boolean saved;
	//is comment favorited
	private boolean favorited;
	//number of times this comment has been favorited
	private int favoriteCount;
	//number of replies to the comment
	private int replyCount;
	//id of Elastic Search
	private String elasticID;
	
	/**
	 * This constructor constructs a comment without an image.
	 * @param user The user that comment belongs to.
	 * @param aTitle The title of the comment.
	 * @param aComment The comment body.
	 * @param loc The geo location of comment.
	 */
	public Comment(Commenter user, String aTitle, String aComment, GeoLocation loc) 
	{
		this.commenter = user;
		this.title = aTitle;
		this.comment = aComment;
		this.geolocation = loc;
		this.commentDate = new Date();
		this.replies = new ArrayList<Comment>();
		this.hasImage = false;
		this.favorited = false;
		this.saved = false;
		this.favoriteCount = 0;
		this.replyCount = 0;
		this.elasticID = null;
	}

	/**
	 * This constructor constructs a comment with an image.
	 * @param user The user that comment belongs to.
	 * @param aTitle The title of the comment.
	 * @param aComment The comment body.
	 * @param loc The geo location of comment.
	 * @param aImage The image of comment.
	 */
	public Comment(Commenter user, String aTitle, String aComment, GeoLocation loc, Image aImage) 
	{
		this.commenter = user;
		this.title = aTitle;
		this.comment = aComment;
		this.geolocation = loc;
		this.img = aImage;
		this.commentDate = new Date();
		this.replies = new ArrayList<Comment>();
		this.hasImage = true;
		this.favorited = false;
		this.saved = false;
		this.favoriteCount = 0;
		this.replyCount = 0;
		this.elasticID = null;
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
	}

	public Date getDate() {
		return commentDate;
	}

	public void setDate(Date date) {
		this.commentDate = date;
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

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
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

	/**
	 * Increment the number times the comment was favorited.
	 */
	public void increaseFavCount()
	{
		this.favoriteCount++;
	}
	
	/**
	 * Increase the number of times the comment has been replied to.
	 */
	public void increaseReplyCount()
	{
		this.replyCount++;
	}
	
	/**
	 * Converts date to a string using SimpleDateFormat
	 * @return String of comment date.
	 */
	public String dateToString()
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
		return df.format(this.commentDate);
	}

}
