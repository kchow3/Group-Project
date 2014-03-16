package cs.ualberta.ca.tunein;

import java.io.Serializable;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
	 * Constructor constructs a geolocation
	 */
	public GeoLocation() 
	{
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	
}
