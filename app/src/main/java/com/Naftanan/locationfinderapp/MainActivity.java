package com.Naftanan.locationfinderapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLocation, editTextLatitude, editTextLongitude;
    private Button searchButton, addButton, deleteButton, updateButton;
    private TextView resultView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextLocation = findViewById(R.id.editTextTextMultiLine2);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        searchButton = findViewById(R.id.button3);
        addButton = findViewById(R.id.buttonAdd);
        deleteButton = findViewById(R.id.buttonDelete);
        updateButton = findViewById(R.id.buttonUpdate);
        resultView = findViewById(R.id.resultView); // TextView to display result (latitude & longitude)

        databaseHelper = new DatabaseHelper(this);


        // Search location
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextLocation.getText().toString().trim();
                if (!address.isEmpty()) {
                    Cursor cursor = databaseHelper.queryLocation(address);
                    if (cursor != null && cursor.moveToFirst()) {
                        @SuppressLint("Range") double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE));
                        @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE));
                        resultView.setText("Latitude: " + latitude + ", Longitude: " + longitude);
                    } else {
                        resultView.setText("Location not found");
                    }
                    cursor.close();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter an address to search", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add location
        // Add location
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextLocation.getText().toString().trim();
                String latitudeStr = editTextLatitude.getText().toString().trim();
                String longitudeStr = editTextLongitude.getText().toString().trim();

                if (!address.isEmpty() && !latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);
                    databaseHelper.addLocation(address, latitude, longitude);
                    Toast.makeText(MainActivity.this, "Location added successfully", Toast.LENGTH_SHORT).show();

                    // Refresh resultView to show the added location
                    resultView.setText("Location added: Latitude: " + latitude + ", Longitude: " + longitude);
                    clearInputFields();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter address, latitude, and longitude", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Delete location
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextLocation.getText().toString().trim();
                if (!address.isEmpty()) {
                    int rowsAffected = databaseHelper.deleteLocation(address);
                    if (rowsAffected > 0) {
                        Toast.makeText(MainActivity.this, "Location deleted successfully", Toast.LENGTH_SHORT).show();

                        // Refresh resultView to indicate the deletion
                        resultView.setText("Location deleted: " + address);
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter an address to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Update location
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextLocation.getText().toString().trim();
                String latitudeStr = editTextLatitude.getText().toString().trim();
                String longitudeStr = editTextLongitude.getText().toString().trim();

                if (!address.isEmpty() && !latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);
                    int rowsAffected = databaseHelper.updateLocation(address, latitude, longitude);
                    if (rowsAffected > 0) {
                        Toast.makeText(MainActivity.this, "Location updated successfully", Toast.LENGTH_SHORT).show();

                        // Refresh resultView to show the updated location
                        resultView.setText("Location updated: Latitude: " + latitude + ", Longitude: " + longitude);
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter address, latitude, and longitude", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearInputFields() {
        editTextLocation.setText("");
        editTextLatitude.setText("");
        editTextLongitude.setText("");
    }
}