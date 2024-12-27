package com.example.gestion_etablissment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStaffActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)

        val editTextFirstName: EditText = findViewById(R.id.editTextFirstName)
        val editTextLastName: EditText = findViewById(R.id.editTextLastName)
        val editTextOccupation: EditText = findViewById(R.id.editTextOccupation)
        val btnAddStaff: Button = findViewById(R.id.btnAddStaff)

        btnAddStaff.setOnClickListener {
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val occupation = editTextOccupation.text.toString()

            // Handle adding the staff member (e.g., save to backend or local storage)
            Toast.makeText(this, "Staff Added: $firstName $lastName", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}
