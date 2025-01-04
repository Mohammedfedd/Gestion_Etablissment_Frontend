package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateStaffActivity extends AppCompatActivity {

    private EditText editFirstName, editLastName, editOccupation;
    private Button btnSaveUpdate;
    private long staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        // Initialize views
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editOccupation = findViewById(R.id.edit_occupation);
        btnSaveUpdate = findViewById(R.id.btn_save_update);
        ImageButton btnReturn = findViewById(R.id.btn_return); // ImageButton for return action

        // Retrieve intent extras
        Intent intent = getIntent();
        staffId = intent.getLongExtra("id", -1);
        editFirstName.setText(intent.getStringExtra("firstName"));
        editLastName.setText(intent.getStringExtra("lastName"));
        editOccupation.setText(intent.getStringExtra("occupation"));

        // Set click listeners
        btnSaveUpdate.setOnClickListener(v -> updateStaff());

        // Handle return button click
        btnReturn.setOnClickListener(v -> finish());
    }

    private void updateStaff() {
        String updatedFirstName = editFirstName.getText().toString();
        String updatedLastName = editLastName.getText().toString();
        String updatedOccupation = editOccupation.getText().toString();

        if (updatedFirstName.isEmpty() || updatedLastName.isEmpty() || updatedOccupation.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct the URL for the update request
        String url = "http://10.0.2.2:8080/api/personnel/" + staffId
                + "?prenom=" + updatedFirstName
                + "&nom=" + updatedLastName
                + "&occupation=" + updatedOccupation;

        // Send the request
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(this, "Staff updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after successful update
                },
                error -> Toast.makeText(this, "Error updating staff", Toast.LENGTH_SHORT).show()
        );

        // Add request to Volley queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
