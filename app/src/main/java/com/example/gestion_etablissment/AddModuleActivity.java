package com.example.gestion_etablissment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddModuleActivity extends AppCompatActivity {

    private EditText editTextModuleName;
    private Button btnSaveModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        editTextModuleName = findViewById(R.id.editTextModuleName);
        btnSaveModule = findViewById(R.id.btnSaveModule);

        btnSaveModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moduleName = editTextModuleName.getText().toString();
                // Add logic to save the module
                finish(); // Close activity after adding the module
            }
        });
    }
}
