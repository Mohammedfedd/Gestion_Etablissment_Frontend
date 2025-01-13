package com.example.gestion_etablissment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextNiveau;
    private Button btnSaveStudent;
    private LinearLayout modulesContainer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextNiveau = findViewById(R.id.editTextNiveau);
        btnSaveStudent = findViewById(R.id.btnSaveStudent);
        modulesContainer = findViewById(R.id.modulesContainer);
        ImageButton btnReturn = findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(v -> finish());

        sharedPreferences = getSharedPreferences("StudentGrades", MODE_PRIVATE); // SharedPreferences for saving grades

        btnSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String niveau = editTextNiveau.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || niveau.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                addStudent(firstName, lastName, niveau);
            }
        });

        editTextNiveau.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                fetchModulesAndDisplay();
            }
        });
    }

    private void addStudent(String firstName, String lastName, String niveau) {
        String url = "http://10.0.2.2:8080/api/etudiants";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddStudentActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStudentActivity.this, "Error adding student", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("prenom", firstName);
                params.put("nom", lastName);
                params.put("niveau", niveau);
                return params;
            }
        };

        requestQueue.add(request);
    }

    private void fetchModulesAndDisplay() {
        String niveau = editTextNiveau.getText().toString().trim();
        String url = "http://10.0.2.2:8080/api/modules?niveau=" + niveau;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        modulesContainer.removeAllViews();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject moduleObject = response.getJSONObject(i);
                                String moduleName = moduleObject.getString("name");

                                LinearLayout moduleLayout = new LinearLayout(AddStudentActivity.this);
                                moduleLayout.setOrientation(LinearLayout.HORIZONTAL);

                                TextView moduleNameTextView = new TextView(AddStudentActivity.this);
                                moduleNameTextView.setText(moduleName);
                                moduleLayout.addView(moduleNameTextView);

                                EditText gradeEditText = new EditText(AddStudentActivity.this);
                                gradeEditText.setHint("Enter Grade");

                                // Set the previously saved grade for the module (if any)
                                String savedGrade = sharedPreferences.getString(moduleName, "");
                                if (!savedGrade.isEmpty()) {
                                    gradeEditText.setText(savedGrade);
                                }

                                moduleLayout.addView(gradeEditText);
                                modulesContainer.addView(moduleLayout);

                                // Save the grade when it changes
                                gradeEditText.setOnFocusChangeListener((v, hasFocus) -> {
                                    if (!hasFocus) {
                                        saveGrade(moduleName, gradeEditText.getText().toString());
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddStudentActivity.this, "Error parsing modules", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStudentActivity.this, "Error fetching modules", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void saveGrade(String moduleName, String grade) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(moduleName, grade); // Save the grade for the specific module
        editor.apply();
    }
}
