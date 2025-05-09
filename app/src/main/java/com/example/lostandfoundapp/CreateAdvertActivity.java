package com.example.lostandfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.*;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CreateAdvertActivity extends AppCompatActivity {
    EditText etName, etPhone, etDescription, etDate, etLocation;
    RadioGroup radioGroup;
    Button btnSave, btnGetLocation;
    DatabaseHelper db;
    FusedLocationProviderClient fusedClient;
    static final int AUTOCOMPLETE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        db = new DatabaseHelper(this);
        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        radioGroup = findViewById(R.id.radioGroup);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        btnSave = findViewById(R.id.btnSave);
        btnGetLocation = findViewById(R.id.btnGetLocation);

        etLocation.setFocusable(false);
        etLocation.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST);
        });

        btnGetLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            fusedClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addresses.isEmpty()) {
                            etLocation.setText(addresses.get(0).getAddressLine(0));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        btnSave.setOnClickListener(v -> {
            String postType = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            boolean inserted = db.insertData(postType, etName.getText().toString(), etPhone.getText().toString(),
                    etDescription.getText().toString(), etDate.getText().toString(), etLocation.getText().toString());
            Toast.makeText(this, inserted ? "Saved!" : "Failed", Toast.LENGTH_SHORT).show();
            if (inserted) finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            etLocation.setText(place.getAddress());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
