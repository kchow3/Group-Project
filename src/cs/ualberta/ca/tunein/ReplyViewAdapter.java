package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import cs.ualberta.ca.tunein.network.ElasticSearchOperations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View
 * ReplyViewAdapter Class:
 * This is part of the view class for comments. This class is used to 
 * display the replies to a comment using an expandable list view.
 * This class is not complete yet.
 */
public class ReplyViewAdapter extends BaseExpandableListAdapter{
	
	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	//public string that tags the extra of the topic comment that is passed to CommentPageActivity
	public final static String EXTRA_TOPIC_COMMENT = "cs.ualberta.ca.tunein.topicComment";
	
	private Context context;
	//holder for elements in the row
	private ViewHolder holder;
	private ArrayList<Comment> replies;
	//parent topic comment corresponding to the comment being viewed
	private Comment topicComment;
	
	/**
	 * View holder that holds the elements of a
	 * custom row that improves scrolling.
	 */
	public static class ViewHolder
	{
		TextView textViewReply;
		TextView textViewReplyRowCount;
		TextView textViewReplyUser;
		TextView textViewReplyDate;
		
		Button buttonRowReply;
		Button buttonReplyView;
	}
	
	/**
	 * Constructor that constructs a ReplyViewAdapter
	 * @param context The context of the activity that constructs this adapter.
	 * @param replies The array list of replies to be displayed.
	 */
	public ReplyViewAdapter(Context context, ArrayList<Comment> replies, Comment topComment)
	{
		this.context = context;
		this.replies = replies;
		this.topicComment = topComment;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Comment> list = replies.get(groupPosition).getReplies();
		return list.get(childPosition);
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		holder = new ViewHolder();
		
		//create inflater
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//get rowView from inflater
		View rowView = null;
		rowView = inflater.inflate(R.layout.reply_view_row, parent, false);
		
		//set all the elements in the custom comment row
		holder.textViewReply = (TextView) rowView.findViewById(R.id.textViewReply);
		holder.textViewReplyRowCount = (TextView) rowView.findViewById(R.id.textViewReplyRowCount);
		holder.textViewReplyUser = (TextView) rowView.findViewById(R.id.textViewReplyUser);
		holder.textViewReplyDate = (TextView) rowView.findViewById(R.id.textViewReplyDate);
		holder.buttonRowReply = (Button) rowView.findViewById(R.id.buttonRowReply);
		holder.buttonReplyView = (Button) rowView.findViewById(R.id.buttonReplyView);
		
		//set text of textviews
		holder.textViewReply.setText(replies.get(groupPosition).getReplies().get(childPosition).getComment());
		holder.textViewReplyRowCount.setText("Replies: " + Integer.toString(replies.get(groupPosition).getReplies().get(childPosition).getReplyCount()));
		holder.textViewReplyUser.setText(replies.get(groupPosition).getReplies().get(childPosition).getCommenter().getName());
		holder.textViewReplyDate.setText(replies.get(groupPosition).getReplies().get(childPosition).dateToString());
		
		//set onclick listeners for buttons and the tag for position
		//int array to send indexes
		int arr[] = {groupPosition, childPosition};
		holder.buttonRowReply.setOnClickListener(replyChildBtnClick);
		holder.buttonRowReply.setTag(arr);
		holder.buttonReplyView.setOnClickListener(viewChildBtnClick);
		holder.buttonReplyView.setTag(arr);
		
		//set the holder
        rowView.setTag(holder);
		
		return rowView;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<Comment> list = replies.get(groupPosition).getReplies();
		return list.size();
	}


	@Override
	public Object getGroup(int groupPosition) {
		return replies.get(groupPosition);
	}


	@Override
	public int getGroupCount() {
		return replies.size();
	}


	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
	
		//declare a new holder
		holder = new ViewHolder();
				
		//create inflater
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
		//get rowView from inflater
		View rowView = null;
		rowView = inflater.inflate(R.layout.reply_view_row, parent, false);
		
		//set all the elements in the custom comment row
		holder.textViewReply = (TextView) rowView.findViewById(R.id.textViewReply);
		holder.textViewReplyRowCount = (TextView) rowView.findViewById(R.id.textViewReplyRowCount);
		holder.textViewReplyUser = (TextView) rowView.findViewById(R.id.textViewReplyUser);
		holder.textViewReplyDate = (TextView) rowView.findViewById(R.id.textViewReplyDate);
		holder.buttonRowReply = (Button) rowView.findViewById(R.id.buttonRowReply);
		holder.buttonReplyView = (Button) rowView.findViewById(R.id.buttonReplyView);
		
		//set text of textviews
		holder.textViewReply.setText(replies.get(groupPosition).getComment());
		holder.textViewReplyRowCount.setText("Replies: " + Integer.toString(replies.get(groupPosition).getReplyCount()));
		holder.textViewReplyUser.setText(replies.get(groupPosition).getCommenter().getName());
		holder.textViewReplyDate.setText(replies.get(groupPosition).dateToString());
		
		//set onclick listeners for buttons and the tag for position
		holder.buttonRowReply.setOnClickListener(replyParentBtnClick);
		holder.buttonRowReply.setTag(groupPosition);
		holder.buttonReplyView.setOnClickListener(viewParentBtnClick);
		holder.buttonReplyView.setTag(groupPosition);
		
		//set the holder
        rowView.setTag(holder);
		
		return rowView;
	}


	@Override
	public boolean hasStableIds() {
		return true;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	
	/**
	 * This click listener will send user to CommentViewPage of the comment
	 * that they clicked view on.
	 */
	private OnClickListener viewChildBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	int index[] = (int[])v.getTag();
	    	Comment aComment = replies.get(index[0]).getReplies().get(index[1]);
	    	Intent intent = new Intent(context, CommentPageActivity.class);
	    	intent.putExtra(EXTRA_COMMENT, aComment);
	    	intent.putExtra(EXTRA_TOPIC_COMMENT, topicComment);
	    	context.startActivity(intent);
	    }
	};
	
	/**
	 * This click listener will send user a comment creation dialog box
	 * so that they can reply to a comment that they clicked reply on.
	 */
	private OnClickListener replyChildBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	final int index[] = (int[])v.getTag();
	    	LayoutInflater inflater = LayoutInflater.from(context);
			final View createView = inflater.inflate(R.layout.create_comment_view, null);

			final TextView inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
			final TextView inputComment = (EditText) createView.findViewById(R.id.editTextComment);
			final ImageView inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String title = inputTitle.getText().toString();
			            String text = inputComment.getText().toString();
			            
			            //create comment with image else one with no image
			            if (inputImage.getVisibility() == View.VISIBLE) 
			            {
			            	inputImage.buildDrawingCache();
			            	Bitmap bmp = inputImage.getDrawingCache();
			            	Image img = new Image(bmp);
		            	
			            	//temp geo location
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername((Activity)context);
			            	String id = userCntrl.loadUserid((Activity)context);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		//current comment that is replied to using tag and get parent position
			        		Comment currentComment = replies.get(index[0]).getReplies().get(index[1]);
			        		//new comment reply
			        		Comment newComment  = new Comment(user, title, text, loc, img);
			        		CommentController cntrl = new CommentController(currentComment);
			        		cntrl.addReply(newComment);
			        		
			        		ElasticSearchOperations.putCommentModel(topicComment);
			        		
			        		updateReplyView(replies);
			            } 
			            else 
			            {	                
			            	//temp geo location
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername((Activity)context);
			            	String id = userCntrl.loadUserid((Activity)context);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		//current comment that is replied to using tag and get parent position
			        		Comment currentComment = replies.get(index[0]).getReplies().get(index[1]);
			        		//new comment reply
			        		Comment newComment  = new Comment(user, title, text, loc);
			        		CommentController cntrl = new CommentController(currentComment);
			        		cntrl.addReply(newComment);
			        		
			        		ElasticSearchOperations.putCommentModel(topicComment);
			        		
			        		updateReplyView(replies);
			        		
			            }
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};
	
	/**
	 * This click listener will send user to CommentViewPage of the comment
	 * that they clicked view on.
	 */
	private OnClickListener viewParentBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	int index = (Integer) v.getTag();
	    	Comment aComment = replies.get(index);
	    	Intent intent = new Intent(context, CommentPageActivity.class);
	    	intent.putExtra(EXTRA_COMMENT, aComment);
	    	intent.putExtra(EXTRA_TOPIC_COMMENT, topicComment);
	    	context.startActivity(intent);
	    }
	};
	
	/**
	 * This click listener will send user a comment creation dialog box
	 * so that they can reply to a comment that they clicked reply on.
	 */
	private OnClickListener replyParentBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	final int i = (Integer) v.getTag();
	    	LayoutInflater inflater = LayoutInflater.from(context);
			final View createView = inflater.inflate(R.layout.create_comment_view, null);

			final TextView inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
			final TextView inputComment = (EditText) createView.findViewById(R.id.editTextComment);
			final ImageView inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String title = inputTitle.getText().toString();
			            String text = inputComment.getText().toString();
			            
			            //create comment with image else one with no image
			            if (inputImage.getVisibility() == View.VISIBLE) 
			            {
			            	inputImage.buildDrawingCache();
			            	Bitmap bmp = inputImage.getDrawingCache();
			            	Image img = new Image(bmp);
		            	
			            	//temp geo location
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername((Activity)context);
			            	String id = userCntrl.loadUserid((Activity)context);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		//current comment that is replied to using tag and get parent position
			        		Comment currentComment = replies.get(i);
			        		//new comment reply
			        		Comment newComment  = new Comment(user, title, text, loc, img);
			        		CommentController cntrl = new CommentController(currentComment);
			        		cntrl.addReply(newComment);
			        		
			        		ElasticSearchOperations.putCommentModel(topicComment);
			        		
			        		updateReplyView(replies);
			            } 
			            else 
			            {	                
			            	//temp geo location
			            	UserController userCntrl = new UserController();
			            	String username = userCntrl.loadUsername((Activity)context);
			            	String id = userCntrl.loadUserid((Activity)context);
			        		Commenter user = new Commenter(username, id);
			        		
			        		GeoLocation loc = new GeoLocation(5, 10);
			        		
			        		//current comment that is replied to using tag and get parent position
			        		Comment currentComment = replies.get(i);
			        		//new comment reply
			        		Comment newComment  = new Comment(user, title, text, loc);
			        		CommentController cntrl = new CommentController(currentComment);
			        		cntrl.addReply(newComment);
			        		
			        		ElasticSearchOperations.putCommentModel(topicComment);
			        		
			        		updateReplyView(replies);
			        		
			            }
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};

	/**
	 * Method to refresh the view and change the reference in
	 * this adapter to the one passed in.
	 * @param replies The new replies list.
	 */
	public void updateReplyView(ArrayList<Comment> replies) {
		this.replies = replies;
		notifyDataSetChanged();
	}
	
}
