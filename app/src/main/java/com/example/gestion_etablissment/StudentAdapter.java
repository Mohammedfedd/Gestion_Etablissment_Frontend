package com.example.gestion_etablissment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;
    private List<String> modules;

    public StudentAdapter(List<Student> students, List<String> modules) {
        this.students = students;
        this.modules = modules;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.nameTextView.setText(student.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, modules);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.moduleSpinner.setAdapter(adapter);


        holder.moduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {
                String selectedModule = modules.get(pos);
                String grade = student.getGrades().get(selectedModule);
                holder.gradeTextView.setText(grade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                holder.gradeTextView.setText("No grade available");
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Spinner moduleSpinner;
        TextView gradeTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_student_name);
            moduleSpinner = itemView.findViewById(R.id.spinner_module);
            gradeTextView = itemView.findViewById(R.id.text_student_grade);
        }
    }


    public static class Student {
        private String name;
        private Map<String, String> grades;

        public Student(String name, Map<String, String> grades) {
            this.name = name;
            this.grades = grades;
        }

        public String getName() {
            return name;
        }

        public Map<String, String> getGrades() {
            return grades;
        }
    }
}
