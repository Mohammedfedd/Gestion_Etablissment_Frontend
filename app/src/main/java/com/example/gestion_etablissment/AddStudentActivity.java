package com.example.gestion_etablissment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    private ModuleGradeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        EditText firstNameField = findViewById(R.id.editTextFirstName);
        EditText lastNameField = findViewById(R.id.editTextLastName);
        RecyclerView recyclerView = findViewById(R.id.recyclerModules);
        Button saveButton = findViewById(R.id.btnSaveStudent);

        // Replace with API call
        List<String> modules = Arrays.asList("Math", "Science", "History");
        adapter = new ModuleGradeAdapter(modules);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        saveButton.setOnClickListener(v -> {
            String firstName = firstNameField.getText().toString();
            String lastName = lastNameField.getText().toString();

            // Retrieve the grades map from the adapter
            Map<String, String> gradesMap = adapter.getGrades();

            // Convert the grades from String to Integer (if needed)
            for (Map.Entry<String, String> entry : gradesMap.entrySet()) {
                String module = entry.getKey();
                String gradeString = entry.getValue();

                // You can add a check here to ensure the grade is a valid integer
                try {
                    int grade = Integer.parseInt(gradeString);  // Convert to integer
                    // Handle saving the grade for the module
                } catch (NumberFormatException e) {
                    // Handle invalid grade input
                    // For example, set a default value or show an error message
                }
            }

            // Handle data saving logic here (e.g., send to backend)

            // Go back to the previous page
            finish();
        });
    }
}
