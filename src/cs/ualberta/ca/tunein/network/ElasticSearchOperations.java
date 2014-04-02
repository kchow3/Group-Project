package cs.ualberta.ca.tunein.network;

//THE FOLLOWING CODE IS FROM https://github.com/zjullion/PicPosterComplete/tree/master/src/ca/ualberta/cs/picposter/network

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.ReplyViewAdapter;
import cs.ualberta.ca.tunein.ThreadList;

/**
 * Handles sending Coments to the server and executing searches on the
 * server. Most of the code in this class is based on:
 * https://github.com/rayzhangcl/ESDemo
 */
public class ElasticSearchOperations implements ElasticSearchOperationsInterface {

	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t03/datetest/";
	//public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t03/TuneIn/";
	public static final String LOG_TAG = "ElasticSearch";
	
	public final static String SORTLONG = "cs.ualberta.ca.tunein.sortLong";
	public final static String SORTLAT = "cs.ualberta.ca.tunein.sortLat";

	private static Gson GSON = null;

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#postCommentModel(cs.ualberta.ca.tunein.Comment)
	 */
	@Override
	public void postCommentModel(final Comment model) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL);

				try {
					request.setEntity(new StringEntity(GSON.toJson(model)));
					Log.v("GSON", GSON.toJson(model));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding PicPostModel: "
									+ exception.getMessage());
					return;
				}

				HttpResponse response = null;
				try {
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
				} catch (IOException exception) {
					Log.w(LOG_TAG,
							"Error sending PicPostModel: "
									+ exception.getMessage());
				}
				
				String jsonResponse = null;
				try {
					jsonResponse = getEntityContent(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Comment>>(){}.getType();
				ElasticSearchResponse<Comment> esResponse = GSON.fromJson(jsonResponse, elasticSearchResponseType);
				String elasticID = esResponse.getID();

				model.setElasticID(elasticID);

				putCommentModel(model);
			}
		};

		thread.start();
	}
	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#putCommentModel(cs.ualberta.ca.tunein.Comment)
	 */
	@Override
	public void putCommentModel(final Comment model) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + model.getElasticID() + "/");
				String query = GSON.toJson(model);
				Log.w("Query", query);
				try {
					request.setEntity(new StringEntity(query));
					Log.v("GSON", GSON.toJson(model));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding PicPostModel: "
									+ exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
				} catch (IOException exception) {
					Log.w(LOG_TAG,
							"Error sending PicPostModel: "
									+ exception.getMessage());
				}
			}
		};

		thread.start();
	}

	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#getCommentPosts(java.lang.String, cs.ualberta.ca.tunein.Comment, android.content.Context)
	 */
	@Override
	public void getCommentPosts(final String elasticID, final Comment model, final Context cntxt ) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(SERVER_URL + elasticID);
				String responseJson = "";

				try {
					HttpResponse response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());

					responseJson = getEntityContent(response);

					
				} catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}

				Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Comment>>(){}.getType();
				// Now we expect to get a Recipe response
				final ElasticSearchResponse<Comment> esResponse = GSON.fromJson(responseJson, elasticSearchResponseType);
				
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						model.setupComment(esResponse.getSource());
					}
				};

				((Activity) cntxt).runOnUiThread(updateModel);
			}
		};

		thread.start();
	}
	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#getRepliesByParentId(java.lang.String, cs.ualberta.ca.tunein.Comment, android.content.Context, cs.ualberta.ca.tunein.ReplyViewAdapter)
	 */
	@Override
	public void getRepliesByParentId(final String parentID, final Comment model, final Context cntxt, final ReplyViewAdapter adap) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "_search");
				String query = "{\"query\": {\"match\": {\"parentID\": \"" + parentID + "\"}}}";
				String responseJson = "";

				Log.w(LOG_TAG, "query is: " + query);
				try {
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}
				
				try {
					HttpResponse response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
					responseJson = getEntityContent(response);
					}
					//Log.v("GSON", responseJson);		 
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}

				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comment>>() {
				}.getType();
				final ElasticSearchSearchResponse<Comment> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);

				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						model.clear();
						model.addReplies((ArrayList<Comment>) returnedData.getSources());
						ArrayList<Comment> list = model.getReplies();
						for(int i = 0; i < list.size(); i++)
						{
							getReplyReplies(list.get(i), list.get(i).getElasticID(), cntxt);
						}
						adap.notifyDataSetChanged();
					}
				};

				((Activity) cntxt).runOnUiThread(updateModel);
			}
		};

		thread.start();
	}
	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#getReplyReplies(cs.ualberta.ca.tunein.Comment, java.lang.String, android.content.Context)
	 */
	@Override
	public void getReplyReplies(final Comment model, final String parentID, final Context cntxt) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost requestReplyReply  = new HttpPost(SERVER_URL + "_search");
				String queryReplyReply = "{\"query\": {\"match\": {\"parentID\": \"" + parentID + "\"}}}";
				String responseJson = "";

				Log.w(LOG_TAG, "queryReplyReply is: " + queryReplyReply);
				try {
					requestReplyReply.setEntity(new StringEntity(queryReplyReply));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}
				
				try {
					HttpResponse response = client.execute(requestReplyReply);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
					responseJson = getEntityContent(response);
					}
					//Log.v("GSON", responseJson);		 
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}

				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comment>>() {
				}.getType();
				final ElasticSearchSearchResponse<Comment> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						model.clear();
						model.addReplies((ArrayList<Comment>) returnedData.getSources());
					}
				};
				
				((Activity) cntxt).runOnUiThread(updateModel);
			}
		};

		thread.start();
	}

	
	/* (non-Javadoc)
	 * @see cs.ualberta.ca.tunein.network.ElasticSearchOperationsInterface#getCommentPostsByReplyCount(cs.ualberta.ca.tunein.ThreadList, android.content.Context)
	 */
	@Override
	public void getTopicsBySort(final ThreadList modelList, final Context cntxt, final String sort) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "_search/");
				String query = querySortReturn(sort, cntxt);
				String responseJson = "";

				//Log.w(LOG_TAG, "query is: " + query);
				try {
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}
				
				try {
					HttpResponse response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());

					responseJson = getEntityContent(response);
					}
					//Log.v("GSON", responseJson);
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}

				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comment>>() {
				}.getType();
				final ElasticSearchSearchResponse<Comment> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);

				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						modelList.clear();
						modelList.addCommentCollection(returnedData.getSources());
					}
				};

				((Activity) cntxt).runOnUiThread(updateModel);
			}
		};

		thread.start();
	}

	
	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for
	 * Bitmaps.
	 */
	private void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}
	
	/**
	 * get the http response and return json string
	 */
	private String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			json += output;
		}
		return json;
	}
	
	/**
	 * Method to choose which query corresponding to the sort option specified.
	 * @param sort The sort option
	 * @return The query for elastic search
	 */
	private String querySortReturn(String sort, Context cntxt)
	{
		String query = "";
		Log.v("sort", sort);
		if(sort.equals("Date"))
		{
			query = "{\"query\": {\"match\": {\"parentID\": \"0\"}} , " +
					"\"sort\": [ { \"date\": { \"order\": \"desc\",  \"ignore_unmapped\": true } } ] }";
		}

		if(sort.equals("Picture"))
		{
			query = "{\"query\": {\"match\": {\"parentID\": \"0\"}} , " +
					"\"sort\": [ { \"hasImage\": { \"order\": \"desc\",  \"ignore_unmapped\": true } } ] }";
		}
		if(sort.equals("My Location") || sort.equals("Set Location"))
		{	    
			SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
			String lon = prefs.getString(SORTLONG, "0");
			String lat = prefs.getString(SORTLAT, "0");
			String result = lon + ", " + lat;
			query = "{\"query\": {\"match\": {\"parentID\": \"0\"}} , " +
					"\"sort\": [ { \"_geo_distance\": { \"order\": \"desc\",  \"ignore_unmapped\": true, " +
					"\"datetest.geolocation\": ["+result+"], \"unit\": \"km\" } } ] }";
		}
		if(sort.equals("default"))
		{
			//sort by hotness: replycount and favoritecount
			query = "{\"query\": {\"match\": {\"parentID\": \"0\"}} , " +
					"\"sort\": [ { \"replyCount\": { \"order\": \"desc\",  \"ignore_unmapped\": true }," +
					"  \"favoriteCount\": { \"order\": \"desc\",  \"ignore_unmapped\": true } } ] }";
		}
		Log.v("sort", query);
		return query;
	}
}
