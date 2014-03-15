package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CommentController;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.Image;
import cs.ualberta.ca.tunein.User;

public class commentControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentControllerTest() {
		super(MainActivity.class);
	}
	
	Commenter commenter = new Commenter("myName", "ID");
	GeoLocation loc = new GeoLocation(0, 0);
	Comment comment = new Comment(commenter , "Title", "Comment", loc);
	CommentController commentcontroller = new CommentController(comment);
	
	public void testEditText() {
		String new_comment = "Different comment";
		commentcontroller.editText(new_comment);
		assertEquals("Edited text should be 'Different comment'", comment.getComment(), new_comment);
	}
	
	public void testChangeLoc() {
		GeoLocation new_loc = new GeoLocation(1, 1);
		commentcontroller.changeLoc(new_loc);
		assertEquals("Edited geo location should be (1, 1)", comment.getGeolocation(), new_loc);
	}
	
	public void testAddReply() {
		int reply_count = comment.getReplyCount();
		commentcontroller.addReply(comment);
		assertEquals("Comment reply count should be incremented by one", comment.getReplyCount(), reply_count+1);
	}
	
	public void testAddImg() {
		Image new_img = new Image(null);
		commentcontroller.addImg(new_img);
		assertEquals("Image should be new_img", comment.getImg(), new_img);
	}
	
	public void testFavorite() {
		int fav_count = comment.getFavoriteCount();
		commentcontroller.favorite(comment);
		assertEquals("fav count should be incremented by one", comment.getFavoriteCount(), fav_count+1);
	}
	
	public void testEditTitle() {
		String new_title = "Different Title";
		commentcontroller.editTitle(new_title);
		assertEquals("Edited title should be 'Different Title", comment.getTitle(), new_title);
	}
	
	public void testCheckValid() {
		String new_ID = "Different ID";
		Activity act = this.getActivity(); //not sure about how to format this test
		commentcontroller.checkValid(act);
		assertFalse("User ID should match Unique ID", commenter.getUniqueID().equals(new_ID));
	}
}
