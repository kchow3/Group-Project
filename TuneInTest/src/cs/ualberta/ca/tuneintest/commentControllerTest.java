package cs.ualberta.ca.tuneintest;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.graphics.Bitmap;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.GeoLocation;
import cs.ualberta.ca.tunein.MainActivity;
import cs.ualberta.ca.tunein.CommentController;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.Image;

public class commentControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public commentControllerTest() {
		super(MainActivity.class);
	}
	
	private Activity activity;
	private Bitmap bmp;
	
	protected void setUp() throws Exception {
		 super.setUp();
		 setActivityInitialTouchMode(false);
		 activity = getActivity();
	}
	
	Commenter commenter = new Commenter("myName", "ID");
	GeoLocation loc = new GeoLocation();
	Comment comment = new Comment(commenter , "Title", "Comment", loc, "parent");
	CommentController commentcontroller = new CommentController(comment);
	
	public void testEditText() {
		String new_comment = "Different comment";
		commentcontroller.editText(new_comment);
		assertEquals("Edited text should be 'Different comment'", comment.getComment(), new_comment);
	}
	
	public void testChangeLoc() {
		commentcontroller.changeLoc(1.0, 1.0);
		assertEquals("Edited geo longitude should be 1.0", comment.getGeolocation().getLongitude(), 1.0);
		assertEquals("Edited geo latitude should be 1.0", comment.getGeolocation().getLatitude(), 1.0);
	}
	
	public void testAddReply() {
		int reply_count = comment.getReplyCount();
		commentcontroller.addReply("test reply", activity, null, null, false);
		assertEquals("Comment reply count should be incremented by one", comment.getReplyCount(), reply_count+1);
	}
	
	public void testAddImg() {
		Bitmap new_img = this.bmp;
		commentcontroller.addImg(new_img);
		assertNotSame("Image should not be null", comment.getImg(), null);
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
		commentcontroller.checkValid(activity);
		assertFalse("User ID should match Unique ID", commenter.getUniqueID().equals(new_ID));
	}
	
	@Override
	protected void tearDown() throws Exception {
	    super.tearDown();
	}
}
