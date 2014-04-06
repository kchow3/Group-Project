package cs.ualberta.ca.tunein;

import java.io.Serializable;

/**
 * Model
 * GeoLocation Class:
 * This class is a model of the geolocation of a comment,
 * that holds the longitude and latitude of the comment.
 */
public class GeoLocation implements Serializable{

	private double lon;
	private double lat;
	
	/**
	 * Constructor constructs a geolocation
	 */
	public GeoLocation() 
	{
	}

	public double getLongitude() {
		return lon;
	}

	public void setLongitude(double longitude) {
		this.lon = longitude;
	}

	public double getLatitude() {
		return lat;
	}

	public void setLatitude(double latitude) {
		this.lat = latitude;
	}

	
}
