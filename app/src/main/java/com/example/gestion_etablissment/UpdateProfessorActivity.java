package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfessorActivity extends AppCompatActivity {

    private EditText editFirstName, editLastName;
    private Spinner spinnerModule;
    private Button btnSaveUpdate;
    private long professorId;
    private List<Module> moduleList = new ArrayList<>();
    private ArrayAdapter<String> moduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_professor);

        // Initialize views
        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        spinnerModule = findViewById(R.id.spinner_module);
        btnSaveUpdate = findViewById(R.id.btn_save_update);

        // Add return button (arrow) at the top left
        ImageButton btnReturn = findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(v -> finish());

        // Get data from the intent
        Intent intent = getIntent();
        professorId = intent.getLongExtra("professorId", -1);
        String firstName = intent.getStringExtra("professorFirstName");
        String lastName = intent.getStringExtra("professorLastName");
        long professorModuleId = intent.getLongExtra("professorModuleId", -1);  // Get the module ID

        // Pre-fill the EditText fields
        editFirstName.setText(firstName);
        editLastName.setText(lastName);

        // Initialize the adapter for the spinner
        moduleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        moduleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModule.setAdapter(moduleAdapter);

        // Fetch modules from the server
        fetchModules(professorModuleId);

        // Handle save update button click
        btnSaveUpdate.setOnClickListener(v -> updateProfessor());
    }

    private void fetchModules(long selectedModuleId) {
        String url = "http://10.0.2.2:8080/api/modules";  // Replace with your actual URL

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Clear previous modules
                        moduleList.clear();

                        // Parse the response and add modules to the list
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject moduleObject = response.getJSONObject(i);
                            String moduleName = moduleObject.getString("name");
                            long moduleId = moduleObject.getLong("id");

                            // Exclude module with ID 0 from being added to the list
                            if (moduleId != 0) {
                                moduleList.add(new Module(moduleId, moduleName));
                            }
                        }

                        // Populate the spinner with valid module names
                        List<String> moduleNames = new ArrayList<>();
                        for (Module module : moduleList) {
                            moduleNames.add(module.getName());
                        }

                        moduleAdapter.clear();
                        moduleAdapter.addAll(moduleNames);
                        moduleAdapter.notifyDataSetChanged();

                        // Set the selected module in the spinner after the spinner is populated
                        setSpinnerSelection(selectedModuleId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(UpdateProfessorActivity.this, "Error fetching modules", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(UpdateProfessorActivity.this, "Error fetching modules", Toast.LENGTH_SHORT).show());

        // Add request to the queue
        Volley.newRequestQueue(this).add(request);
    }

    private void setSpinnerSelection(long selectedModuleId) {
        // Ensure the spinner selection matches the professor's current module
        for (int i = 0; i < moduleList.size(); i++) {
            if (moduleList.get(i).getId() == selectedModuleId) {
                spinnerModule.setSelection(i);  // Directly set the selected item to match the professor's module
                break;
            }
        }
    }

    private void updateProfessor() {
        String updatedFirstName = editFirstName.getText().toString();
        String updatedLastName = editLastName.getText().toString();
        String updatedModuleName = spinnerModule.getSelectedItem().toString();

        if (updatedFirstName.isEmpty() || updatedLastName.isEmpty() || updatedModuleName.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        long selectedModuleId = getModuleIdFromName(updatedModuleName);

        if (selectedModuleId == -1) {
            Toast.makeText(this, "Invalid module selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:8080/api/professeurs/" + professorId
                + "?prenom=" + updatedFirstName
                + "&nom=" + updatedLastName
                + "&moduleId=" + selectedModuleId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(this, "Professor updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(this, "Error updating professor", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private long getModuleIdFromName(String moduleName) {
        for (Module module : moduleList) {
            if (module.getName().equals(moduleName)) {
                return module.getId();
            }
        }
        return -1;
    }

    // Module class to represent modules
    public static class Module {
        private long id;
        private String name;

        public Module(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
