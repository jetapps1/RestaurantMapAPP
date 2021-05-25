package com.example.restaurantmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnNewPlace, btnShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNewPlace = findViewById(R.id.btnNewPlace);
        btnShowAll = findViewById(R.id.btnShowAll);
        ArrayList<AddPlace> places = getIntent().getParcelableArrayListExtra("List");

        btnNewPlace.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, AddRestaurant.class);
            startActivity(intent);
        });

        btnShowAll.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ShowAllMaps.class);
            intent.putParcelableArrayListExtra("List", places);
            startActivity(intent);
        });
    }
}