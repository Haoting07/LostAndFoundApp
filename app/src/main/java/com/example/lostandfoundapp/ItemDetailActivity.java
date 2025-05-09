package com.example.lostandfoundapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    TextView tvDetails;
    Button btnRemove;
    DatabaseHelper db;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        db = new DatabaseHelper(this);
        tvDetails = findViewById(R.id.tvDetails);
        btnRemove = findViewById(R.id.btnRemove);

        itemId = getIntent().getIntExtra("id", -1);

        Cursor res = db.getAllData();
        while (res.moveToNext()) {
            if (res.getInt(0) == itemId) {
                String details = "Type: " + res.getString(1) + "\n" +
                        "Name: " + res.getString(2) + "\n" +
                        "Phone: " + res.getString(3) + "\n" +
                        "Desc: " + res.getString(4) + "\n" +
                        "Date: " + res.getString(5) + "\n" +
                        "Location: " + res.getString(6);
                tvDetails.setText(details);
                break;
            }
        }

        btnRemove.setOnClickListener(v -> {
            db.deleteData(String.valueOf(itemId));
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}


