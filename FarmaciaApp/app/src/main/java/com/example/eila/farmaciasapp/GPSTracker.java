package com.example.eila.farmaciasapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

    private final String TAG = "GPSTracker";

    private final Context context;

    // flag for GPS status
    private boolean isGPSEnabled = false;

    // flag for network status
    private boolean isNetworkEnabled = false;

    // flag for GPS status
    private boolean canGetLocation = false;

    private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds // millis in a sec, sec in a min, # of min
    // many humans tend to walk at about 1.4 m/s or 84 m/min, so check every 7 seconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 7/60;

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;
        getCurrentLocation();
    }

    public Location getCurrentLocation() {
        try {
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);

            // set our GPS and Network status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // check status
            if (isGPSEnabled || isNetworkEnabled) {
                this.canGetLocation = true;

                // Beginning in Android 6.0 (API level 23), users grant permissions to apps while the app is running, not when they install the app
                if(ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

                    // get location from GPS if available
                    if (isGPSEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d(TAG, "GPS Enabled");
                    } else {
                        Log.e(TAG, "GPS Disabled");
                    }
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }

                    if (location == null) {
                        // get location from network location if GPS is off
                        if(isNetworkEnabled) {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                    MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d(TAG, "Network");
                        } else {
                            Log.e(TAG, "Network Disabled");
                        }

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public String updateWithNewLocation(Location location) {
        String latLongString;

        if (location != null){
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        }else{
            latLongString = "No Location";
        }

        return latLongString;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // returns true if Network OR GPS is allowed in permissions
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // START_STICKY tells the OS to recreate the service after it has enough memory
        // and call onStartCommand() again with a null intent
        // todo: if there are not any pending start commands to be delivered to the service, it will be called with a null intent object, so you must take care to check for this.
        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        // todo: check using float Location.distanceTo(Location) for distance from 'it' if player is 'not it', and 'not it' if player is 'it'
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}