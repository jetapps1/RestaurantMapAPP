package com.example.restaurantmap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double coords_lat;
    Double coords_long;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int pid = android.os.Process.myPid();
        String whiteList = "logcat -P '" + pid + "'";
        try {
            Runtime.getRuntime().exec(whiteList).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            name = extras.getString("name");
            coords_lat = extras.getDouble("lat");
            coords_long = extras.getDouble("long");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng marker = new LatLng(coords_lat, coords_long);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in " + name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
    }
}