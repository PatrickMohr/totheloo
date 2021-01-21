package com.example.tothelooclient;

import androidx.fragment.app.FragmentActivity;

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
import android.renderscript.Float3;
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
import java.util.List;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private ArrayList<MarkerLocation> markerTestListe;
    private Map<Marker, String> markerHashMap;
    private String testString;



    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //variablen
    private Boolean mLocationPermissionGranted = false;

    //currentMarker
    private double currentMarkerLat;
    private double currentMarkerLng;
    private String currentMarkerTitle;
    private String currentMarkerId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        markerHashMap = new HashMap<>();
        markerTestListe = new ArrayList<>();
       /* markerTestListe.add(new MarkerLocation(53.5625, 9.9573, "toilette 1" + " Bewertung:" + "4,5"));
        markerTestListe.add(new MarkerLocation(53.5725, 9.9673, "toilette 2" + " Bewertung: " + " 5"));
        markerTestListe.add(new MarkerLocation(53.5825, 9.4573, "toilette 3" + " Bewertung:" + "3,7"));
        markerTestListe.add(new MarkerLocation(56.5625, 9.9373, "toilette 4" + " Bewertung:" + "1,1"));
        markerTestListe.add(new MarkerLocation(53.4625, 9.8573, "toilette 5" + " Bewertung:" + "2,0"));
        markerTestListe.add(new MarkerLocation(53.8625, 10.9573, "toilette 6" + " Bewertung:" + "4,7"));
  */
        testString = "42;WonderLoo;53.5625;9.9573;4,5 \n 43;easyfalls;53.5725;9.9673;5 \n 44;Quite Place;53.5825;9.4573;3,7 \n 45;Sprinkler Anlage;56.5625;9.9373;1,1 \n 46;Das Geschäft;53.4625;9.8573;2,0  \n 47;Lass es Krachen;53.8625;10.9573;4,7"
        ;

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready ");
        int i = 0;
        mMap = googleMap;
        getLocationPermission();
        getdevicelocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

      /*  String [] toiletten = testString.split("\n");
        String string1 = toiletten [0];
        String string2 = toiletten [1];
        String [] parts = string1.split(";");
        String id = parts [0];
        String name = parts [1];
        String Lat = parts [2];
        String Lng = parts [3];
        String Rating = parts [4];
        String [] parts2 = string2.split(";");
        String id2 = parts2 [0];
        String name2 = parts2 [1];
        String Lat2 = parts2 [2];
        String Lng2 = parts2 [3];
        String Rating2 = parts2 [4];

        double value1 = Double.parseDouble(Lat);
        double value2 = Double.parseDouble(Lng);
        double value3 = Double.parseDouble(Lat2);
        double value4 = Double.parseDouble(Lng2);

        MarkerLocation testMarker = new MarkerLocation(Double.parseDouble(Lat),Double.parseDouble(Lng),name +" "+ "Bewertung:"+" "+ Rating);
        MarkerLocation testMarker2 = new MarkerLocation(Double.parseDouble(Lat2),Double.parseDouble(Lng2),name2 +" "+ "Bewertung:"+" "+ Rating2);
        setMarker(value1,value2,testMarker.getTitle());
        setMarker(value3,value4,testMarker2.getTitle()); */

        String [] toiletten = testString.split("\n");
        for (String string : toiletten) {
            String [] parts = string.split(";");
            String id = parts [0];
            String name = parts [1];
            String Lat = parts [2];
            String Lng = parts [3];
            String Rating = parts [4];


             {


                setMarker(++i,Double.parseDouble(Lat), Double.parseDouble(Lng), name, "Bewertung:" + " " + Rating,id);

            }
        }


      /*  for (MarkerLocation marker : markerTestListe) {

            setMarker(marker.getLatitude(), marker.getLongitude(), marker.getTitle());
        }
     */
    }


    public void openMainActivity3() {
        Intent markerDetails = new Intent(this, MainActivity3.class);
        markerDetails.putExtra("Latitude",currentMarkerLat);
        markerDetails.putExtra("Longitude",currentMarkerLng);
        markerDetails.putExtra("Title",currentMarkerTitle);
        markerDetails.putExtra("id",currentMarkerId);
        startActivity(markerDetails);
    }

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
            = new GoogleMap.OnInfoWindowClickListener(){
        @Override
        public void onInfoWindowClick(Marker marker) {
            currentMarkerLat = marker.getPosition().latitude;
            currentMarkerLng = marker.getPosition().longitude;
            currentMarkerTitle = marker.getTitle();
            currentMarkerId = markerHashMap.get(marker);

            openMainActivity3();
           /* Toast.makeText(MapsActivity.this,
                    "onInfoWindowClick():\n" +
                            marker.getPosition().latitude + "\n" +
                            marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();
                    */
        }
    };




    private void setMarker(int markerNumber,double Latitude, double Longitude, String Title, String snip, String id) {
        LatLng toilet = new LatLng(Latitude, Longitude);
    Marker m = mMap.addMarker(new MarkerOptions().position(toilet).title(Title).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc_mf)).snippet(snip));
    markerHashMap.put(m,id);
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat:" + latLng.latitude + ",lng:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this,
                "onMapClick:\n" + latLng.latitude + " : " + latLng.longitude,
                Toast.LENGTH_LONG).show();
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
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecrurityException" + e.getMessage());
        }

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



}
