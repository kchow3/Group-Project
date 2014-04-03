package cs.ualberta.ca.tunein.network;

import android.content.Context;
import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.CommentViewAdapter;
import cs.ualberta.ca.tunein.Commenter;
import cs.ualberta.ca.tunein.ReplyViewAdapter;
import cs.ualberta.ca.tunein.ThreadList;

public interface ElasticSearchOperationsInterface {

	/**
	 * Sends a Comment to the server. Does nothing if the request fails.
	 * 
	 * @param model
	 *            a Comment
	 * @return String of the elasticid of commnet posted.
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
	 * the specified sort option in the parameters, either: hotness(default),
	 * date, picture, my location, set location
	 * Code from:
	 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/search-request-sort.html
	 * @param modelList ThreadList that will be filled.
	 * @param activity Activity that calls this method.
	 */
	public abstract void getTopicsBySort(ThreadList modelList,
			Context cntxt, String sort);

	/**
	 * Sends a commenter profile to the server.
	 * @param model The profile to be sent.
	 */
	public abstract void postProfileModel(Commenter model);
	
	/**
	 * Updates a profile to the server.
	 * @param model The profile to be sent.
	 */
	public abstract void putProfileModel(Commenter model);
	
	/**
	 * Gets the profile from the server.
	 * @param id The elastic id to search for profile.
	 * @param model The profile that user will be loaded into.
	 * @param cntxt Context of application.
	 */
	public abstract void getProfileModel(String id, Commenter model, Context cntxt);

}