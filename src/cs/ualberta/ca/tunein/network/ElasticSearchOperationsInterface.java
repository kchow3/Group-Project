package cs.ualberta.ca.tunein.network;

import android.content.Context;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.ReplyViewAdapter;
import cs.ualberta.ca.tunein.ThreadList;

public interface ElasticSearchOperationsInterface {

	/**
	 * Sends a Comment to the server. Does nothing if the request fails.
	 * 
	 * @param model
	 *            a Comment
	 */
	public abstract void postCommentModel(Comment model);

	/**
	 * Method used for updating a comment on elasticsearch by posting using
	 * the elastic id.
	 * @param model comment to be posted
	 */
	public abstract void putCommentModel(Comment model);

	/**
	 * Searches the server for Comments 
	 * @param model
	 *            the ThreadLis to clear and then fill with the new
	 *            data
	 * @param activity
	 *            a TopicListActivity
	 */
	public abstract void getCommentPosts(String parentID, Comment model,
			Context cntxt);

	/**
	 * Searches the server for Comment replies corresponding to parent id.
	 * @param model
	 *            the ThreadLis to clear and then fill with the new
	 *            data
	 * @param activity
	 *            a TopicListActivity
	 */
	public abstract void getRepliesByParentId(String parentID, Comment model,
			Context cntxt, ReplyViewAdapter adap);

	public abstract void getReplyReplies(Comment model, String parentID,
			Context cntxt);

	/**
	 * Method to get comments from elasticsearch and sort them based on
	 * reply count which will give us comments that are currently "hot"
	 * Code from:
	 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/search-request-sort.html
	 * @param modelList ThreadList that will be filled.
	 * @param activity Activity that calls this method.
	 */
	public abstract void getCommentPostsByReplyCount(ThreadList modelList,
			Context cntxt);

	/**
	 * Method to get comments from elasticsearch and sort them based on having a picture.
	 * Code from:
	 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/search-request-sort.html
	 * @param modelList ThreadList that will be filled.
	 * @param cntxt Activity that calls this method.
	 */
	public abstract void getCommentPostsByPictures(ThreadList modelList, Context cntxt);

}