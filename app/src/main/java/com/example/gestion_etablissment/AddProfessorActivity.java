package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddProfessorActivity extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextOccupation;
    private Button btnSaveProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_professors_activity);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextOccupation = findViewById(R.id.editTextOccupation);
        btnSaveProfessor = findViewById(R.id.btnSaveProfessor);

        btnSaveProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfessor();
            }
        });
    }

    private void saveProfessor() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String occupation = editTextOccupation.getText().toString();

        // Add professor to database (Placeholder logic)
        // Example: Save professor to your backend or database

        Intent resultIntent = new Intent();
        resultIntent.putExtra("firstName", firstName);
        resultIntent.putExtra("lastName", lastName);
        resultIntent.putExtra("occupation", occupation);

        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
