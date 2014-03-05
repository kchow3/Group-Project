package cs.ualberta.ca.tunein;

public class GeoLocation {

	private long longitude;
	private long latitude;
	
	public GeoLocation(long lon, long lat) 
	{
		this.longitude = lon;
		this.latitude = lat;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	
}
