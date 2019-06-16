package com.example.neyesek.activity;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.neyesek.MapScreen;
import com.example.neyesek.R;
import com.example.neyesek.model.NearByApiResponse;
import com.google.android.gms.maps.CameraUpdate;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Random;

public class SearchNearByPlaces extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private Button btnFind;
    private TextView placeName1,timePlace;
    private LocationRequest mLocationRequest;
    String test;
    public Location location;
    private int PROXIMITY_RADIUS = 300;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();
    Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();


        btnFind = (Button) findViewById(R.id.btnFind);
        placeName1 = (TextView) findViewById(R.id.textView3);
        timePlace = (TextView) findViewById(R.id.textView9);
        if(!isGooglePlayServicesAvailable()){
            Toast.makeText(this,"Google Play Services not available.",Toast.LENGTH_LONG).show();
            finish();
        }else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, 0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);

            }
        } else {
            buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void onFindClick(View view){
        findPlaces("restaurant");
        View b = findViewById(R.id.table);
        b.setVisibility(View.VISIBLE);
        /*View c = findViewById(R.id.btnFind);
        c.setEnabled(false);
        c.setVisibility(View.INVISIBLE);*/
    }

    public void findPlaces(String placeType){
        Call<NearByApiResponse> call = MapScreen.getApp().getApiService().getNearbyPlaces(placeType, location.getLatitude() + "," + location.getLongitude(), PROXIMITY_RADIUS);

        call.enqueue(new Callback<NearByApiResponse>() {
            @Override
            public void onResponse(Call<NearByApiResponse> call, Response<NearByApiResponse> response) {
                try {
                    googleMap.clear();
                    // This loop will go through all the results and add marker on each location.
                    Random generator = new Random();
                    int i = generator.nextInt(response.body().getResults().size());

                    Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                    Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                    String placeName = response.body().getResults().get(i).getName();
                    String vicinity = response.body().getResults().get(i).getVicinity();
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(lat, lng);
                    placeName1.setText(placeName);
                    test = placeName;
                    c.setTime(date);
                    c.add(Calendar.MINUTE, 15);
                    Date newDate = c.getTime();
                    timePlace.setText(dateFormat.format(newDate));
                    // Location of Marker on Map
                    markerOptions.position(latLng);
                    // Title for Marker
                    markerOptions.title(placeName + " : " + vicinity);
                    // Color or drawable for marker
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    // add marker
                    Marker m = googleMap.addMarker(markerOptions);
                    // move map camera
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(cameraUpdate);

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NearByApiResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                t.printStackTrace();
                PROXIMITY_RADIUS += 10000;
            }
        });
    }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "Location Permission has been denied.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Could not connect google api",Toast.LENGTH_LONG).show();
    }


    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30);
        mLocationRequest.setFastestInterval(30);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            this.location = location;
            if(!btnFind.isEnabled()){
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                btnFind.setEnabled(true);
            }
        }
    }
}
