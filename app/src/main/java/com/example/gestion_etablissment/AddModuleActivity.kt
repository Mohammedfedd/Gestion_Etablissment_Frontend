package com.example.gestion_etablissment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddModuleActivity : AppCompatActivity() {

    private lateinit var editTextModuleName: EditText
    private lateinit var btnSaveModule: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_module)

        editTextModuleName = findViewById(R.id.editTextModuleName)
        btnSaveModule = findViewById(R.id.btnSaveModule)

        btnSaveModule.setOnClickListener {
            val moduleName = editTextModuleName.text.toString()
            // Add logic to save the module
            finish() // Close activity after adding the module
        }
    }
}
