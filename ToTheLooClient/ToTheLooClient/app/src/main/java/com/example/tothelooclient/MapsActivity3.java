package com.example.tothelooclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Toast;


import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback {




        private static final String TAG = "MapsActivity3";
        private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
        private static final float DEFAULT_ZOOM = 15f;


        private GoogleMap mMap;
        private FusedLocationProviderClient mFusedLocationProviderClient;
        private ArrayList<LatLng> listPoints;

        //variablen
        private Boolean mLocationPermissionGranted = false;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            listPoints = new ArrayList<>();
        }




        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d(TAG, "onMapReady: map is ready ");
            mMap = googleMap;
            getLocationPermission();
            getdevicelocation();
            mMap.setMyLocationEnabled(true);
            mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

            // Möglichkeit neue Marker auf die Karte zu setzten;

         mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override
            public void onMapLongClick(LatLng latLng) {

                listPoints.add(latLng);
                setMarker(latLng.latitude,latLng.longitude,"Hinzufügen");
                if (listPoints.size() > 1) {
                    listPoints.clear();
                    mMap.clear();
                }
                if (listPoints.size() == 1) {
                    Toast.makeText(MapsActivity3.this,
                            "Zum hinzufügen nochmal auf den Marker Klicken",
                            Toast.LENGTH_LONG).show();
                }

                }
        });



        }



        public void openMainActivity3() {
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        }

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
            = new GoogleMap.OnInfoWindowClickListener(){
        @Override
        public void onInfoWindowClick(Marker marker) {
            setResult(MainActivity4.RESULT_OK,
                    new Intent().putExtra("latitude", marker.getPosition().latitude).putExtra("longitude", marker.getPosition().longitude));
            finish();
           /* Toast.makeText(MapsActivity.this,
                    "onInfoWindowClick():\n" +
                            marker.getPosition().latitude + "\n" +
                            marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();
                    */
        }
    };

        private void setMarker(double Latitude, double Longitude, String Title) {
            LatLng toilet = new LatLng(Latitude, Longitude);
            mMap.addMarker(new MarkerOptions().position(toilet).title(Title).icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_icon)));
        }


    private void getdevicelocation() {

        Log.d(TAG, "getdevicelocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity3.this, "unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecrurityException" + e.getMessage());
        }

    }

        // neue location methode



        private void moveCamera(LatLng latLng, float zoom) {
            Log.d(TAG, "moveCamera: moving the camera to: lat:" + latLng.latitude + ",lng:" + latLng.longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }

        private void getLocationPermission() {
            Log.d(TAG, "getLocationPermission: getting location permission");
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {
                    ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
                }
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            Log.d(TAG, "onRequestPermissionsResult: called.");
            mLocationPermissionGranted = false;

            switch (requestCode) {
                case LOCATION_PERMISSION_REQUEST_CODE: {
                    if (grantResults.length > 0) {
                        for (int i = 0; i < grantResults.length; i++)
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                mLocationPermissionGranted = false;
                                Log.d(TAG, "onRequestPermissionsResult: permission failed");
                                return;
                            }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                }
            }
        }


   /*     GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
                = new GoogleMap.OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker) {
                openMainActivity3();
            Toast.makeText(MapsActivity.this,
                    "onInfoWindowClick():\n" +
                            marker.getPosition().latitude + "\n" +
                            marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();

            }
        }; */


        }




