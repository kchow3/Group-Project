package cs.ualberta.ca.tunein;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View
 * CommentViewAdapter Class:
 * This class is used to create the list view for the topic list
 * that contains comments. This class is used in the TopicListActivity
 * to create a list view and load in comments.
 * Intent code from:
 * http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android
 */
public class CommentViewAdapter extends ArrayAdapter<Comment>{
	
	//public string that tags the extra of the comment that is passed to CommentPageActivity
	public final static String EXTRA_COMMENT = "cs.ualberta.ca.tunein.comment";
	
	//comment controller
	private CommentController commentController;
	private FavoriteController favoriteController;
	private Context context;
	//holder for the elements in the row
	private ViewHolder holder;
	private ArrayList<Comment> commentList;
	
	//dialog elements
	private View createView;
	private TextView inputTitle;
	private TextView inputComment;
	private ImageView inputImage;
	
	/**
	 * This static class is used for holding the elements of
	 * a custom comment row. This is used for smoother scrolling
	 * of the list view.
	 * Code from :
	 * http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	 */
	private static class ViewHolder 
	{
		TextView textViewTitle;
		TextView textViewDate;
		TextView textViewUser;
		TextView textViewFavCount;
		TextView textViewReplyCount;
		TextView textViewSaved;
		TextView textViewFavorited;
		Button buttonView;
		Button buttonReply;
		Button buttonFav;
		Button buttonSave;
    }
	
	/**
	 * Constructor constructs a CommentViewAdapter.
	 * @param context The context of the activity that calls this class.
	 * @param commentList The comment list that is to be in the list view.
	 */
	public CommentViewAdapter(Context context, ArrayList<Comment> commentList) 
	{
		super(context, R.layout.comment_view_row, commentList);
		this.context = context;
		this.commentList = commentList;
		favoriteController = new FavoriteController();
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
		
		//set all the elements in the custom comment row
		holder.textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
		holder.textViewDate = (TextView) rowView.findViewById(R.id.textViewDate);
		holder.textViewUser = (TextView) rowView.findViewById(R.id.textViewUser);
		holder.textViewFavCount = (TextView) rowView.findViewById(R.id.textViewFavCount);
		holder.textViewReplyCount = (TextView) rowView.findViewById(R.id.textViewReplyCount);
		holder.buttonView = (Button) rowView.findViewById(R.id.buttonView);
		holder.buttonReply = (Button) rowView.findViewById(R.id.buttonReply);
		holder.buttonFav = (Button) rowView.findViewById(R.id.buttonFav);
		holder.buttonSave = (Button) rowView.findViewById(R.id.buttonSave);
		
		//set text of textviews
		holder.textViewTitle.setText(commentList.get(position).getTitle());
		holder.textViewDate.setText(commentList.get(position).dateToString());
		holder.textViewUser.setText(commentList.get(position).getCommenter().getName());
		holder.textViewFavCount.setText("Favs: " +Integer.toString(commentList.get(position).getFavoriteCount()));
		holder.textViewReplyCount.setText("Replies: " + Integer.toString(commentList.get(position).getReplyCount()));
		
		//set visibility if comment is in favorites
		if(favoriteController.inFav(commentList.get(position)))
		{
			holder.textViewFavorited.setVisibility(View.VISIBLE);
		}
		
		//set onclick listeners for buttons and the tag for position
		holder.buttonView.setOnClickListener(viewBtnClick);
		holder.buttonView.setTag(position);
		holder.buttonReply.setOnClickListener(replyBtnClick);
		holder.buttonReply.setTag(position);
		holder.buttonFav.setOnClickListener(favBtnClick);
		holder.buttonFav.setTag(position);
		holder.buttonSave.setOnClickListener(saveBtnClick);
		holder.buttonSave.setTag(position);
		
		//set the holder
        rowView.setTag(holder);
		
		return rowView;
	}
	
	/**
	 * This method is used to refresh the list view
	 * @param threadList The comment list that the list view will show.
	 */
	public void updateThreadView(ArrayList<Comment> commentList)
	{
		this.commentList = commentList;
		notifyDataSetChanged();
	}
	
	/**
	 * Method to just refresh the thread view without
	 * assigning a new comment list.
	 */
	public void refreshThreadView()
	{
		notifyDataSetChanged();
	}
	
	/**
	 * This click listener will send user to CommentViewPage of the comment
	 * that they clicked view on.
	 */
	private OnClickListener viewBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	int index = (Integer) v.getTag();
	    	Comment aComment = commentList.get(index);
	    	Intent intent = new Intent(context, CommentPageActivity.class);
	    	intent.putExtra(EXTRA_COMMENT, aComment);
	    	context.startActivity(intent);
	    }
	};
	
	/**
	 * This click listener will send user a comment creation dialog box
	 * so that they can reply to a comment that they clicked reply on.
	 * Bitmap code:
	 * http://stackoverflow.com/questions/8490474/cant-compress-a-recycled-bitmap
	 */
	private OnClickListener replyBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	final int i = (Integer)v.getTag();
	    	setupDialogs();
			AlertDialog dialog = new AlertDialog.Builder(context)
			    .setTitle("Create Comment")
			    .setView(createView)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String title = inputTitle.getText().toString();
			            String text = inputComment.getText().toString();
			            
			            Comment currentComment = commentList.get(i);
		        		commentController = new CommentController(currentComment);
			            //create comment with image else one with no image
			            if (inputImage.getVisibility() == View.VISIBLE) 
			            {
			            	inputImage.buildDrawingCache(true);
			            	Bitmap bitmap = inputImage.getDrawingCache(true).copy(Config.RGB_565, false);
			            	inputImage.destroyDrawingCache();
			            	commentController.addReplyImg(currentComment.getElasticID(), (Activity) context, title, text, bitmap, false);
			            } 
			            else 
			            {	                        		
			            	commentController.addReply(currentComment.getElasticID(), (Activity) context, title, text, false);
			        		
			            } 
			            refreshThreadView();
			        }
			    })
			    .setNegativeButton("Cancel", null).create();
			dialog.show();
	    }
	};
	
	/**
	 * This click listener will favortie the comment that the user clicked
	 * favorite on.
	 */
	private OnClickListener favBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    	final int i = (Integer)v.getTag();
	    	Comment currentComment = commentList.get(i);
	    	favoriteController = new FavoriteController();
	    	favoriteController.addtoFav(context, currentComment);
	    	refreshThreadView();
	    }
	};
	
	/**
	 * This click listener will save the comment that the user clicked save on.
	 */
	private OnClickListener saveBtnClick = new OnClickListener() 
	{
	    public void onClick(View v)
	    {
	    }
	};
	
	/**
	 * This method is for setting up the dialog boxes.
	 */
	private void setupDialogs()
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		createView = inflater.inflate(R.layout.create_comment_view, null);

		inputTitle = (EditText) createView.findViewById(R.id.textViewInputTitle);
		inputComment = (EditText) createView.findViewById(R.id.editTextComment);
		inputImage = (ImageView) createView.findViewById(R.id.imageViewUpload);
	}
}
