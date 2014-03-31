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
	boolean network_enabled = false;
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
	 * @param context The application context of the current activity.
	 * @return
	 */
	public void getLocation(Context context)
	{
		this.context = context;
		if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		//exceptions will be thrown if provider is not permitted.
		try{gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
		try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}
		
		//don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled)
        {
        	buildAlertMessageNoGps();
        }
		
        if(gps_enabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        if(network_enabled)
        {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
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
            lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    /**
     * This location listener is for retrieving location updates from network
     */
    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            loc.setLongitude(location.getLongitude());
            loc.setLatitude(location.getLatitude());
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
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
             lm.removeUpdates(locationListenerNetwork);

             Location net_loc=null, gps_loc=null;
             if(gps_enabled)
                 gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(network_enabled)
                 net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             
             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                 {
                     loc.setLongitude(gps_loc.getLongitude());
                     loc.setLatitude(gps_loc.getLatitude());
                 }
                 else
                 {
                     loc.setLongitude(net_loc.getLongitude());
                     loc.setLatitude(net_loc.getLatitude());
                 }
                 return;
             }
             
             if(gps_loc!=null){
                 loc.setLongitude(gps_loc.getLongitude());
                 loc.setLatitude(gps_loc.getLatitude());
                 return;
             }
             
             if(net_loc!=null){
                 loc.setLongitude(net_loc.getLongitude());
                 loc.setLatitude(net_loc.getLatitude());
                 return;
             }
             
             loc.setLongitude((Double) null);
             loc.setLatitude((Double) null);
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
