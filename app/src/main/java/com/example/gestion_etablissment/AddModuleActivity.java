package com.example.gestion_etablissment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddModuleActivity extends AppCompatActivity {

    private EditText moduleNameEditText, moduleNiveauEditText; // Added for niveau
    private Button addButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        moduleNameEditText = findViewById(R.id.editTextModuleName);
        moduleNiveauEditText = findViewById(R.id.editTextModuleNiveau); // Initialize niveau field
        addButton = findViewById(R.id.btnSaveModule);

        ImageButton btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> finish());

        addButton.setOnClickListener(v -> {
            String moduleName = moduleNameEditText.getText().toString();
            String moduleNiveau = moduleNiveauEditText.getText().toString(); // Get niveau input
            if (!moduleName.isEmpty() && !moduleNiveau.isEmpty()) {
                addModule(moduleName, moduleNiveau); // Pass both moduleName and niveau
            } else {
                Toast.makeText(AddModuleActivity.this, "Module name and niveau are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addModule(String moduleName, String moduleNiveau) {
        String url = "http://10.0.2.2:8080/api/modules";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Toast.makeText(AddModuleActivity.this, "Module added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(AddModuleActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", moduleName);
                params.put("niveau", moduleNiveau); // Add the niveau parameter
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}

