package cs.ualberta.ca.tunein.network;

//THE FOLLOWING CODE IS FROM https://github.com/zjullion/PicPosterComplete/tree/master/src/ca/ualberta/cs/picposter/network

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs.ualberta.ca.tunein.Comment;
import cs.ualberta.ca.tunein.ThreadList;
import cs.ualberta.ca.tunein.TopicListActivity;

/**
 * Handles sending PicPostModels to the server and executing searches on the
 * server. Most of the code in this class is based on:
 * https://github.com/rayzhangcl/ESDemo
 */
public class ElasticSearchOperations {

	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t03/testing";
	//public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t03/TuneIn";
	public static final String LOG_TAG = "ElasticSearch";

	private static Gson GSON = null;

	/**
	 * Sends a Comment to the server. Does nothing if the request fails.
	 * 
	 * @param model
	 *            a Comment
	 */
	public static void pushCommentModel(final Comment model) {
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

	/**
	 * Searches the server for Comments with the given searchTerm in their
	 * text.
	 * 
	 * @param searchTerm
	 *            the single world term to search for
	 * @param model
	 *            the ThreadLis to clear and then fill with the new
	 *            data
	 * @param activity
	 *            a TopicListActivity
	 */
	public static void getCommentPosts(final ThreadList modelList, final Activity activity) {
		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "/_search/");
				String query = "{\"query\": {\"query_string\": {\"default_field\": \"title\",\"query\": \"*"
						+ "" + "*\"}}}";
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

					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent()));

					String output = reader.readLine();
					while (output != null) {
						responseJson += output;
						output = reader.readLine();
					}
					Log.v("GSON", responseJson);
				} catch (IOException exception) {
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

				activity.runOnUiThread(updateModel);
			}
		};

		thread.start();
	}

	/**
	 * Constructs a Gson with a custom serializer / desserializer registered for
	 * Bitmaps.
	 */
	private static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}
}
