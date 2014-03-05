package cs.ualberta.ca.tunein;

public class GeoLocation {

	private int longitude;
	private int latitude;
	
	public GeoLocation(int lon, int lat) 
	{
		this.longitude = lon;
		this.latitude = lat;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	
}
