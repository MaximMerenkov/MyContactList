package com.example.mycontactlist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class ContactMapSensors extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener gpsListener;
    LocationListener networkListener;
    Location currentBestLocation;
    final int PERMISSION_REQUEST_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_map);
        initListButton();
        initMapButton();
        initSettingsButton();
        initGetLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void initGetLocation(){

        Button locationButton = (Button)findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(ContactMapSensors.this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactMapSensors.this,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                                Snackbar.make(findViewById(R.id.sensors_map),
                                        "MyContactList requires this permission to locate " +
                                                "your contacts", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ActivityCompat.requestPermissions(
                                                        ContactMapSensors.this,
                                                        new String[]{
                                                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                                                        PERMISSION_REQUEST_LOCATION);
                                            }
                                        })
                                        .show();

                            } else {
                                ActivityCompat.requestPermissions(ContactMapSensors.this, new
                                                String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_LOCATION);
                            }
                        } else {
                            startLocationUpdates();
                        }
                    } else {

                        startLocationUpdates();
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error requesting permission",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void initListButton() {

        ImageButton imageButtonList = (ImageButton) findViewById(R.id.imageButtonList);
        imageButtonList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapSensors.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }


    private void initMapButton() {
        ImageButton mapButton = (ImageButton) findViewById(R.id.imageButtonMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactMapSensors.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void initSettingsButton() {

        ImageButton imageButtonList = (ImageButton) findViewById(R.id.imageButtonSettings);
        imageButtonList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapSensors.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
    private void startLocationUpdates(){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            locationManager = (LocationManager) getBaseContext().
                    getSystemService(Context.LOCATION_SERVICE);

            gpsListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (isBetterLocation(location)) {
                        currentBestLocation = location;
                        TextView txtLatitude = (TextView) findViewById(R.id.textLatitudeBest);
                        TextView txtLongitude = (TextView) findViewById(R.id.textLongitudeBest);
                        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracyBest);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                    }

                    TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                    TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                    TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);

                    txtLatitude.setText(String.valueOf(location.getLatitude()));
                    txtLongitude.setText(String.valueOf(location.getLongitude()));
                    txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }


            };
            networkListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (isBetterLocation(location)) {
                        currentBestLocation = location;
                        TextView txtLatitude = (TextView) findViewById(R.id.textLatitudeBest);
                        TextView txtLongitude = (TextView) findViewById(R.id.textLongitudeBest);
                        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracyBest);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                    }

                    TextView txtLatitude = (TextView) findViewById(R.id.textLatitudeNetwork);
                    TextView txtLongitude = (TextView) findViewById(R.id.textLongitudeNetwork);
                    TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracyNetwork);

                    txtLatitude.setText(String.valueOf(location.getLatitude()));
                    txtLongitude.setText(String.valueOf(location.getLongitude()));
                    txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }


            };

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,0,0,networkListener);


        } catch (Exception e){
            Toast.makeText(getBaseContext(), "Error, Location not available",
                    Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          String permissions[],int[]grantResults){
        switch(requestCode){
            case PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length>0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startLocationUpdates();
                }else{
                    Toast.makeText(ContactMapSensors.this,
                            "MyContactList will not locate your contacts.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isBetterLocation(Location location) {
        boolean isBetter = false;
        if (currentBestLocation == null) {
            isBetter = true;
        }
        else if (location.getAccuracy() <= currentBestLocation.getAccuracy()) {
            isBetter = true;
        }
        else if (location.getTime() - currentBestLocation.getTime() > 5*60*1000) {
            isBetter = true;
        }
        return isBetter;
    }


}

