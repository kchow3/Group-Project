package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
