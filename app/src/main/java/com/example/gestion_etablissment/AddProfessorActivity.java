package com.example.gestion_etablissment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddProfessorActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName;
    private Spinner spinnerModules;
    private Button btnSaveProfessor;

    private List<String> moduleNames = new ArrayList<>();
    private List<Long> moduleIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_professors_activity);

        // Initialize views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        spinnerModules = findViewById(R.id.spinnerModules);
        btnSaveProfessor = findViewById(R.id.btnSaveProfessor);

        // Add return button (arrow) at the top left
        ImageButton btnReturn = findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(v -> finish());

        // Fetch module data to populate the Spinner
        fetchModuleData();

        btnSaveProfessor.setOnClickListener(v -> saveProfessor());
    }

    private void fetchModuleData() {
        String url = "http://10.0.2.2:8080/api/modules"; // Replace with your API endpoint

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    moduleNames.clear();
                    moduleIds.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject moduleObject = response.getJSONObject(i);
                            String moduleName = moduleObject.getString("name");
                            Long moduleId = moduleObject.getLong("id");

                            // Add module name and id to the lists
                            moduleNames.add(moduleName);
                            moduleIds.add(moduleId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Set up the spinner with module names
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, moduleNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerModules.setAdapter(adapter);

                },
                error -> Toast.makeText(AddProfessorActivity.this, "Error fetching modules", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void saveProfessor() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        Long selectedModuleId = moduleIds.get(spinnerModules.getSelectedItemPosition());

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:8080/api/professeurs" +
                "?prenom=" + firstName +
                "&nom=" + lastName +
                "&moduleId=" + selectedModuleId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(AddProfessorActivity.this, "Professor added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(AddProfessorActivity.this, "Error adding professor", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
