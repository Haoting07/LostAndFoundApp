package com.example.lostandfoundapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.database.Cursor;

import java.io.IOException;
import java.util.List;

public class ShowOnMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        db = new DatabaseHelper(this);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Cursor res = db.getAllData();
        while (res.moveToNext()) {
            String title = res.getString(2);
            String snippet = res.getString(4);
            String address = res.getString(6);
            LatLng latLng = getLatLngFromAddress(address);
            if (latLng != null) {
                mMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(snippet));
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-25.2744, 133.7751), 4));
    }

    private LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> locations = geocoder.getFromLocationName(address, 1);
            if (!locations.isEmpty()) {
                Address a = locations.get(0);
                return new LatLng(a.getLatitude(), a.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
