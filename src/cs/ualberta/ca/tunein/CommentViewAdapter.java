package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CommentViewAdapter extends ArrayAdapter<Comment>{

	private Context context;
	//holder for the elements in the row
	private ViewHolder holder;
	private ArrayList<Comment> commentList;
	
	private static class ViewHolder 
	{
		TextView textViewTitle;
		TextView textViewDate;
		TextView textViewUser;
		TextView textViewFavorited;
		TextView textViewFavCount;
		TextView textViewSaved;
		TextView textViewReplyCount;
		Button buttonView;
		Button buttonReply;
		Button buttonFav;
		Button buttonSave;
    }
	
	public CommentViewAdapter(Context context, int resource, ArrayList<Comment> commentList) 
	{
		super(context, R.layout.comment_view_row, commentList);
		this.context = context;
		this.commentList = commentList;
	}
	
}
