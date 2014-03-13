package cs.ualberta.ca.tunein;

import java.io.Serializable;

/**
 * Model
 * GeoLocation Class:
 * This class is a model of the geolocation of a comment.
 * This class is not comeplete yet.
 */
public class GeoLocation implements Serializable{

	private double longitude;
	private double latitude;
	
	/**
	 * Constructor constructs a geolocation with longitude and latitude
	 * @param lon
	 * @param lat
	 */
	public GeoLocation(double lon, double lat) 
	{
		this.longitude = lon;
		this.latitude = lat;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	
}
