package com.example.gestion_etablissment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddProfessorActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextOccupation: EditText
    private lateinit var btnSaveProfessor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_professors_activity)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextOccupation = findViewById(R.id.editTextOccupation)
        btnSaveProfessor = findViewById(R.id.btnSaveProfessor)

        btnSaveProfessor.setOnClickListener {
            saveProfessor()
        }
    }

    private fun saveProfessor() {
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val occupation = editTextOccupation.text.toString()

        // Add professor to database (Placeholder logic)
        // Example: Save professor to your backend or database

        val resultIntent = Intent()
        resultIntent.putExtra("firstName", firstName)
        resultIntent.putExtra("lastName", lastName)
        resultIntent.putExtra("occupation", occupation)

        setResult(RESULT_OK, resultIntent)

        finish()
    }
}
