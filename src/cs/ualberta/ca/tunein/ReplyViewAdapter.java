package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
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
	
	private Context context;
	//holder for elements in the row
	private ViewHolder holder;
	private ArrayList<Comment> replies;
	
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
	public ReplyViewAdapter(Context context, ArrayList<Comment> replies)
	{
		this.context = context;
		this.replies = replies;
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
	    }
	};

	public void updateThreadView(ArrayList<Comment> replies) {
		this.replies = replies;
		notifyDataSetChanged();
	}
	
	
}
