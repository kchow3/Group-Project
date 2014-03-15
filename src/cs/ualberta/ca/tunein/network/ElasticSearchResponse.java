package cs.ualberta.ca.tunein.network;

//THE FOLLOWING CODE IS FROM https://github.com/zjullion/PicPosterComplete/tree/master/src/ca/ualberta/cs/picposter/network

/**
 * Represents a response from ElasticSearch. Taken from
 * https://github.com/rayzhangcl/ESDemo
 */

public class ElasticSearchResponse<T> {
	String _index;
	String _type;
	String _id;
	int _version;
	boolean exists;
	T _source;
	double max_score;

	public T getSource() {
		return _source;
	}
	
	public String getID()
	{
		return _id;
	}
}
