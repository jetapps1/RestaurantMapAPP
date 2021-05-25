package com.example.restaurantmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class AutoComplete extends AppCompatActivity {
    Button btnSaveLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        btnSaveLocation = findViewById(R.id.btnSaveLocation);
        final Place[] currentPlace = new Place[1];

        btnSaveLocation.setOnClickListener(v-> {
            if(currentPlace.length > 0) {
                if (currentPlace[0].getName() != null) {
                    double latitude;
                    double longitude;
                    String latlong[] = String.valueOf(currentPlace[0].getLatLng()).trim().split(",");

                    latitude = Double.parseDouble(latlong[0].replace("lat/lng: (", ""));
                    longitude = Double.parseDouble(latlong[1].replace(")", ""));

                    Intent intent = new Intent(AutoComplete.this, AddRestaurant.class);
                    intent.putExtra("name", currentPlace[0].getName());
                    intent.putExtra("lat", latitude);
                    intent.putExtra("long", longitude);
                    startActivity(intent);
                }
            }
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d("testing", "Place: " + place.getName() + ", " + place.getId());
                currentPlace[0] = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d("testing", "An error occurred: " + status);
            }
        });

    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Status status = Autocomplete.getStatusFromIntent(data);
        Log.d("testing", status.toString());
    }
}