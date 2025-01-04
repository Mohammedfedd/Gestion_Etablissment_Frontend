package com.example.gestion_etablissment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        EditText editTextLastName = findViewById(R.id.editTextLastName);
        EditText editTextOccupation = findViewById(R.id.editTextOccupation);
        ImageButton btnReturn = findViewById(R.id.btn_return); // Use ImageButton
        View btnAddStaff = findViewById(R.id.btnAddStaff); // Leave this as Button or general View

        btnReturn.setOnClickListener(v -> {
            // Handle return button click
            finish(); // Close the activity
        });

        btnAddStaff.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String occupation = editTextOccupation.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || occupation.isEmpty()) {
                Toast.makeText(AddStaffActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            addStaff(firstName, lastName, occupation);
        });
    }

    private void addStaff(String firstName, String lastName, String occupation) {
        String url = "http://10.0.2.2:8080/api/personnel";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(AddStaffActivity.this, "Staff added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(AddStaffActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("nom", firstName);
                    jsonBody.put("prenom", lastName);
                    jsonBody.put("occupation", occupation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(stringRequest);
    }
}
