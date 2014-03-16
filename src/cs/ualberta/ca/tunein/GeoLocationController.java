package cs.ualberta.ca.tunein;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Controller
 * GeoLocationController Class:
 * This class is to get the geoLocation of the application
 * so it can be used in comment creation and for sorting
 * comments based on location.
 *
 */
public class GeoLocationController {

	private GeoLocation loc;
	private Timer timer1;
	private LocationManager lm;
	private boolean gps_enabled = false;
	private Context context;
	
	public GeoLocationController(GeoLocation location){
		loc = location;
	}
	
	/**
	 * This method creates a geoLocation which will contain
	 * coordinates of the application.
	 * This code is from:
	 * http://stackoverflow.com/questions/3145089/what-is-the-simplest-and-most-robust-way-to-get-the-users-current-location-in-a?rq=1
	 * 
	 * @param context
	 * @return
	 */
	public void getLocation(Context context)
	{
		this.context = context;
		if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		//exceptions will be thrown if provider is not permitted.
		try{gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}
		catch(Exception ex){}
		
		//don't start listeners if no provider is enabled
        if(!gps_enabled)
        {
        	buildAlertMessageNoGps();
        }
		
        if(gps_enabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        else
        {
        	return;
        }
        
        timer1=new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        
	}
	
	/**
	 * This location listener is for retrieving location
	 * updates from the gps.
	 */
	LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            loc.setLongitude(location.getLongitude());
            loc.setLatitude(location.getLatitude());
            lm.removeUpdates(this);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    /**
     * This class is used for getting the last know
     * gps location after a delay when retrieving gps
     * location fails.
     */
    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
             lm.removeUpdates(locationListenerGps);

             Location gps_loc=null;
             if(gps_enabled)
                 gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

             if(gps_loc!=null){
                 loc.setLongitude(gps_loc.getLongitude());
                 loc.setLatitude(gps_loc.getLatitude());
                 return;
             }
        }
    }
	
	/**
	 * This method is open a popup box that has settings for GPS and lets user
	 * be able to turn it on.
	 * Code from:
	 * http://stackoverflow.com/questions/843675/how-do-i-find-out-if-the-gps-of-an-android-device-is-enabled
	 */
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                   context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
}
