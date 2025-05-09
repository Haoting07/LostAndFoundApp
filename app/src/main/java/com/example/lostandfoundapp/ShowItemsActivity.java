package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ShowItemsActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> items;
    ArrayList<Integer> ids;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewItems);
        items = new ArrayList<>();
        ids = new ArrayList<>();

        Cursor res = db.getAllData();
        while (res.moveToNext()) {
            ids.add(res.getInt(0));
            items.add(res.getString(2) + ": " + res.getString(4)); // name + description
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("id", ids.get(position));
            startActivity(intent);
        });
    }
}
