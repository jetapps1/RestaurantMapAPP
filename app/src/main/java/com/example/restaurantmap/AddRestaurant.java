package com.example.restaurantmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class AddRestaurant extends AppCompatActivity implements LocationListener {

    TextView editLocation, editPlaceName;
    Button btnCurrentLocation, btnShowOnMap, btnSave;
    String placeName, locationName;
    double latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    ArrayList<AddPlace> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        editLocation = findViewById(R.id.txtLocation);
        editPlaceName = findViewById(R.id.txtPlaceName);
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        btnShowOnMap = findViewById(R.id.btnShowOnMap);
        btnSave = findViewById(R.id.btnSave);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyCA9MmRAkBpnv-gf4PDzEr5oM2qf-W8Q8Y");

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            placeName = extras.getString("name");
            locationName = extras.getString("location");
            latitude = extras.getDouble("lat");
            longitude = extras.getDouble("long");
            editLocation.setText(latitude + ", " + longitude);
            editPlaceName.setText(placeName);
        }

        btnShowOnMap.setOnClickListener(v -> {
            Intent intent = new Intent(AddRestaurant.this, MapsActivity.class);
            intent.putExtra("name", placeName);
            intent.putExtra("lat", latitude);
            intent.putExtra("long", longitude);
            startActivity(intent);
        });

        btnCurrentLocation.setOnClickListener(v -> {
            getLocation();
            editPlaceName.setText("User Location");
            editLocation.setText(latitude + ", " + longitude);
        });

        btnSave.setOnClickListener(v -> {

            places.add(new AddPlace(placeName, latitude, longitude));
            Intent intent = new Intent(AddRestaurant.this, MainActivity.class);
            intent.putParcelableArrayListExtra("List", places);
            startActivity(intent);
        });
    }

    public void goToAutoComplete(View view) {
        Intent intent = new Intent(AddRestaurant.this, AutoComplete.class);
        startActivity(intent);
    }

    private void getLocation() {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(AddRestaurant.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            }
            else{
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationManager.removeUpdates(this);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText(AddRestaurant.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }
}