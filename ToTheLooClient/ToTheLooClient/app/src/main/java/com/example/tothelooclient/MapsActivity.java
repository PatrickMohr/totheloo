package com.example.tothelooclient;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient;

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


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String DEFAULT_LOCATION_NAME = "Hamburg Toilette 1";
    private static final double DEFAULT_LOCATION_LAT = 53.5625;
    private static final double DEFAULT_LOCATION_LNG = 9.9573;
    private static final String DEFAULT_LOCATION_TITLE = "Erste Test Toilette";
    private Marker myMarker;
    ArrayList<LatLng> listPoints;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

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
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready ");
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                if (arg0 != null && arg0.getTitle().equals("Erste Test Toilette")) {
                    Intent intent1 = new Intent(MapsActivity.this, MainActivity3.class);
                    startActivity(intent1);
                }
            }
        });
        getLocationPermission();
        getdevicelocation();
        mMap.setMyLocationEnabled(true);
        // Todo methode für marker aus Datenbank auslesen und erstellen
        setMarker(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG, DEFAULT_LOCATION_TITLE);

        // Methode um neue Marker auf die Karte zu setzten;

      /*  mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //Create marker
                setMarker(latLng.latitude,latLng.longitude,"new Toilette");
               // MarkerOptions markerOptions = new MarkerOptions();
               // markerOptions.position(latLng);
                //Add  marker to the map
               // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
               // mMap.addMarker(markerOptions);
                // request get direction code
            }

        });
        setMarker(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG, DEFAULT_LOCATION_TITLE);
   */


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.equals(myMarker))
                {
                    openMainActivity3();
                }
                return true;
            }


        });


    }

    public void openMainActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    private void setMarker(double Latitude, double Longitude, String Title) {
        LatLng toilet = new LatLng(Latitude, Longitude);
        mMap.addMarker(new MarkerOptions().position(toilet).title(Title).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc_mf)));
        myMarker =  mMap.addMarker(new MarkerOptions().position(toilet).title(Title).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wc_mf)));
        listPoints.add(toilet);
        myMarker.setTag(toilet);
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


    private String getRequestUrl(LatLng start, LatLng dest) {
        // value of origin
        String str_start = "origin" + start.latitude + "," + start.longitude;
        //value of destination
        String str_dest = "destination" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_start +"&" + str_dest + "&" + sensor +"&" +mode;
        //output format
        String output = "json";
        //create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }


    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();

                httpURLConnection.disconnect();
            }
            return responseString;
        }

    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }


    @Override
     protected void onPostExecute(String s) {
            super.onPostExecute(s);

       TaskParser taskParser = new TaskParser();
       taskParser.execute(s);
      }
}

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionParser directionParser = new DirectionParser();
                routes = directionParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;
            PolylineOptions polylineOptions = null;

            for (List <HashMap<String, String>> patch : lists) {
                points = new ArrayList ();
                polylineOptions = new PolylineOptions();

             for (HashMap<String, String> point : patch) {
                 double lat = Double.parseDouble(point.get("lat"));
                 double lon = Double.parseDouble(point.get("lon"));

                 points.add(new LatLng(lat,lon));

             }
             polylineOptions.addAll(points);
             polylineOptions.width(15);
             polylineOptions.color(Color.BLUE);
             polylineOptions.geodesic(true);


            }
         if (polylineOptions != null) {
             mMap.addPolyline(polylineOptions);
         } else {
             Toast.makeText(getApplicationContext(), "Direction not found", Toast.LENGTH_SHORT).show();
         }
        }
    }
}


