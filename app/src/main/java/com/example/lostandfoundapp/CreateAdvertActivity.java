package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText etName, etPhone, etDescription, etDate, etLocation;
    Button btnSave;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        db = new DatabaseHelper(this);

        radioGroup = findViewById(R.id.radioGroup);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String postType = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            boolean inserted = db.insertData(
                    postType,
                    etName.getText().toString(),
                    etPhone.getText().toString(),
                    etDescription.getText().toString(),
                    etDate.getText().toString(),
                    etLocation.getText().toString()
            );
            Toast.makeText(this, inserted ? "Saved!" : "Failed", Toast.LENGTH_SHORT).show();
            if (inserted) finish();
        });
    }
}
