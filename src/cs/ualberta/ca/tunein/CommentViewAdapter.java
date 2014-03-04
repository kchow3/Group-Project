package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//declare a new holder
		holder = new ViewHolder();
		
		//create inflater
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//get rowView from inflater
		View rowView = null;
		rowView = inflater.inflate(R.layout.comment_view_row, parent, false);
		
		holder.textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
		holder.textViewDate = (TextView) rowView.findViewById(R.id.textViewDate);
		holder.textViewUser = (TextView) rowView.findViewById(R.id.textViewUser);
		holder.textViewFavorited = (TextView) rowView.findViewById(R.id.textViewFavorited);
		holder.textViewFavCount = (TextView) rowView.findViewById(R.id.textViewFavCount);
		holder.textViewSaved = (TextView) rowView.findViewById(R.id.textViewSaved);
		holder.textViewReplyCount = (TextView) rowView.findViewById(R.id.textViewReplyCount);
		holder.buttonView = (Button) rowView.findViewById(R.id.buttonView);
		holder.buttonReply = (Button) rowView.findViewById(R.id.buttonReply);
		holder.buttonFav = (Button) rowView.findViewById(R.id.buttonFav);
		holder.buttonSave = (Button) rowView.findViewById(R.id.buttonSave);
		
		//set textviews
		holder.textViewTitle.setText(commentList.get(position).getTitle());
		holder.textViewDate.setText(commentList.get(position).dateToString());
		holder.textViewUser.setText(commentList.get(position).getCommenter().getName());
		holder.textViewFavCount.setText(Integer.toString(commentList.get(position).getFavoriteCount()));
		holder.textViewReplyCount.setText(Integer.toString(commentList.get(position).getReplyCount()));
		
		//if the comment is favorited then text will show Faved! else it is invisible
		if(commentList.get(position).isFavorited())
		{
			holder.textViewFavorited.setVisibility(View.VISIBLE);
			holder.textViewFavorited.setText("Faved!");
		}
		else
		{
			holder.textViewFavorited.setVisibility(View.INVISIBLE);
		}
		
		//if the comment is saved then text will show Saved! else it is invisible
		if(commentList.get(position).isSaved())
		{
			holder.textViewSaved.setVisibility(View.VISIBLE);
			holder.textViewSaved.setText("Saved!");
		}
		else
		{
			holder.textViewSaved.setVisibility(View.INVISIBLE);
		}
		
		
		//set the holder
        rowView.setTag(holder);
		
		return rowView;
	}
	
}
