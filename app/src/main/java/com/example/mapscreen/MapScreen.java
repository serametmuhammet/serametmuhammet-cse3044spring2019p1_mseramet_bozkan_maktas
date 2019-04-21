package com.example.mapscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coordinate = new LatLng(40.9821665,29.0670206);
        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(coordinate, 13.0f);
        googleMap.animateCamera(center);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.9821665,29.0670206))
                .title("Burger King")
                .snippet("Burger King"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.9901674,29.0597809))
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE))
                .title("Buradasın")
                .snippet("Konumum"));
    }
}

