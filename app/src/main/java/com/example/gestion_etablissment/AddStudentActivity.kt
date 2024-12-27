package com.example.gestion_etablissment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddStudentActivity : AppCompatActivity() {

    private lateinit var adapter: ModuleGradeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val firstNameField: EditText = findViewById(R.id.editTextFirstName)
        val lastNameField: EditText = findViewById(R.id.editTextLastName)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerModules)
        val saveButton: Button = findViewById(R.id.btnSaveStudent)

        val modules = listOf("Math", "Science", "History") // Replace with API call
        adapter = ModuleGradeAdapter(modules)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        saveButton.setOnClickListener {
            val firstName = firstNameField.text.toString()
            val lastName = lastNameField.text.toString()
            val grades = adapter.grades

            // Handle data saving logic here (e.g., send to backend)

            // Go back to the previous page
            finish()
        }
    }
}
