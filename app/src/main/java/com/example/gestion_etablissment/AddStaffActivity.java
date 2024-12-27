package com.example.gestion_etablissment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        EditText editTextLastName = findViewById(R.id.editTextLastName);
        EditText editTextOccupation = findViewById(R.id.editTextOccupation);
        Button btnAddStaff = findViewById(R.id.btnAddStaff);

        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String occupation = editTextOccupation.getText().toString();

                // Handle adding the staff member (e.g., save to backend or local storage)
                Toast.makeText(AddStaffActivity.this, "Staff Added: " + firstName + " " + lastName, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
