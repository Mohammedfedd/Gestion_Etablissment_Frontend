package com.example.gestion_etablissment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText editTextPrenom, editTextNom, editTextNiveau;
    private Button btnUpdateStudent;
    private Long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextNom = findViewById(R.id.editTextNom);
        editTextNiveau = findViewById(R.id.editTextNiveau);
        btnUpdateStudent = findViewById(R.id.btnUpdateStudent);

        // Get student details from the intent
        studentId = getIntent().getLongExtra("studentId", -1);
        String prenom = getIntent().getStringExtra("prenom");
        String nom = getIntent().getStringExtra("nom");
        String niveau = getIntent().getStringExtra("niveau");

        // Set the current student data to the fields
        editTextPrenom.setText(prenom);
        editTextNom.setText(nom);
        editTextNiveau.setText(niveau);

        // Update student details when the button is clicked
        btnUpdateStudent.setOnClickListener(v -> updateStudentInfo());
    }

    private void updateStudentInfo() {
        String updatedPrenom = editTextPrenom.getText().toString();
        String updatedNom = editTextNom.getText().toString();
        String updatedNiveau = editTextNiveau.getText().toString();

        String url = "http://10.0.2.2:8080/api/etudiants/" + studentId + "?prenom=" + updatedPrenom + "&nom=" + updatedNom + "&niveau=" + updatedNiveau;

        // Send the PUT request to update the student
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                response -> {
                    // Finish and go back to the previous activity on success
                    finish();
                },
                error -> {
                    Log.e("UpdateStudent", "Error updating student: " + error.getMessage());
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
