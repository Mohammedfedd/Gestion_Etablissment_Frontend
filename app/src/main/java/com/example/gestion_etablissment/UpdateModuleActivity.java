package com.example.gestion_etablissment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateModuleActivity extends AppCompatActivity {
    private EditText editTextModuleName, editTextModuleNiveau;
    private int moduleId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);

        editTextModuleName = findViewById(R.id.edit_text_module_name);
        editTextModuleNiveau = findViewById(R.id.edit_text_module_niveau);
        ImageButton btnReturn = findViewById(R.id.btn_return); // Initialize return button

        moduleId = getIntent().getIntExtra("moduleId", -1);
        String moduleName = getIntent().getStringExtra("moduleName");
        String moduleNiveau = getIntent().getStringExtra("moduleNiveau"); // Get module niveau
        editTextModuleName.setText(moduleName);
        editTextModuleNiveau.setText(moduleNiveau); // Set module niveau in EditText

        btnReturn.setOnClickListener(v -> finish()); // Handle return button click
    }

    public void updateModule(View view) {
        String updatedName = editTextModuleName.getText().toString();
        String updatedNiveau = editTextModuleNiveau.getText().toString();

        if (updatedName.isEmpty() || updatedNiveau.isEmpty()) {
            Toast.makeText(this, "Module name and niveau cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:8080/api/modules/" + moduleId + "?name=" + updatedName + "&niveau=" + updatedNiveau;

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(this, "Module updated", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(this, "Error updating module", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
