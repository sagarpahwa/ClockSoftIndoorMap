package com.clocksoft.indoormap.clocksoftindoormap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.clocksoft.indoormap.clocksoftindoormap.gpsTracker.GPSTracker;

public class SavePoints extends AppCompatActivity {

    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GPSTracker gps;
    private double lat;
    private double lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_points);
        context = getApplicationContext();
        activity = this;
        if (!checkPermission()) {
            requestPermission();
        }
        if (checkPermission()) {
            if (locationFinder()) {

            }
        }
    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }
    public boolean locationFinder() {
        gps = new GPSTracker(this);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            lat = latitude;
            lon = longitude;

//            editor.putString("latitude", String.valueOf(latitude));
//            editor.putString("longitude", String.valueOf(longitude));
//            editor.putString("tracked", "true");
            //   Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //   Toast.makeText(getApplicationContext(), "" + distance(latitude, longitude, latitude + 3, longitude + 4), Toast.LENGTH_SHORT).show();
            if(lat==0.0&&lon==0.0){
                Toast.makeText(SavePoints.this, "GPS is not working fine", Toast.LENGTH_SHORT).show();
                return  false;
            }else {
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lon, Toast.LENGTH_LONG).show();
                return true;
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            return false;
        }
    }


}
