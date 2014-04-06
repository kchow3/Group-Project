package cs.ualberta.ca.tunein;


import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivityProduct {
	
	
	public final static String SORT = "cs.ualberta.ca.tunein.sort";
	//public string that are used to get the sort info
	public final static String SORTLONG = "cs.ualberta.ca.tunein.sortLong";
	public final static String SORTLAT = "cs.ualberta.ca.tunein.sortLat";
	
	private TextView location_text;
	private GeoLocation loc;
	private View createView;
	private TextView inputLong;
	private TextView inputLat;

	public TextView getLocation_text() {
		return location_text;
	}

	public void setLocation_text(TextView location_text) {
		this.location_text = location_text;
	}

	public GeoLocation getLoc() {
		return loc;
	}

	public void setLoc(GeoLocation loc) {
		this.loc = loc;
	}

	public View getCreateView() {
		return createView;
	}

	public void setCreateView(View createView) {
		this.createView = createView;
	}

	public TextView getInputLong() {
		return inputLong;
	}

	public void setInputLong(TextView inputLong) {
		this.inputLong = inputLong;
	}

	public TextView getInputLat() {
		return inputLat;
	}

	public void setInputLat(TextView inputLat) {
		this.inputLat = inputLat;
	}

	public void loadLoc() {
		String result = "@ " + String.valueOf(loc.getLongitude()) + ", "
				+ String.valueOf(loc.getLatitude());
		location_text.setText(result);
	}

	/**
	 * Setup change location dialog box
	 */
	public void setupDialogs(MainActivity mainActivity) {
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		createView = inflater.inflate(R.layout.location_change, null);
		inputLong = (EditText) createView
				.findViewById(R.id.textViewInputChangeLong);
		inputLat = (EditText) createView
				.findViewById(R.id.textViewInputChangeLat);
	}

	/**
	 * Set the location of the sort.
	 * @param myLoc  Is the sort by my location
	 */
	public void setLoc(boolean myLoc, MainActivity mainActivity) {
		SharedPreferences prefs = mainActivity.getSharedPreferences(
				"cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		if (myLoc) 
		{
			prefs.edit()
					.putString(SORTLONG, String.valueOf(loc.getLongitude())).commit();
			prefs.edit()
					.putString(SORTLAT, String.valueOf(loc.getLongitude())).commit();
		} 
		else 
		{
			prefs.edit()
					.putString(SORTLONG, inputLong.getText().toString()).commit();
			prefs.edit()
					.putString(SORTLAT, inputLat.getText().toString()).commit();
		}
	}
	
	public void setSort(String sort, Context cntxt)
	{
		SharedPreferences prefs = cntxt.getSharedPreferences(
			      "cs.ualberta.ca.tunein", Context.MODE_PRIVATE);
		prefs.edit().putString(SORT, sort).commit();
	}
}