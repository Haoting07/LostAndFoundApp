package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnCreateAdvert, btnShowItems, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);
        btnShowMap = findViewById(R.id.btnShowMap);

        btnCreateAdvert.setOnClickListener(v -> startActivity(new Intent(this, CreateAdvertActivity.class)));
        btnShowItems.setOnClickListener(v -> startActivity(new Intent(this, ShowItemsActivity.class)));
        btnShowMap.setOnClickListener(v -> startActivity(new Intent(this, ShowOnMapActivity.class)));
    }
}

