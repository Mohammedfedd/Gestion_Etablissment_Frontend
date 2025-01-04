package com.example.gestion_etablissment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder> {

    private List<Professor> professors;
    private Context context;
    private static final String DELETE_URL = "http://10.0.2.2:8080/api/professeurs/";  // Update with your URL

    public ProfessorAdapter(List<Professor> professors, Context context) {
        this.professors = professors;
        this.context = context;
    }

    @Override
    public ProfessorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professor, parent, false);
        return new ProfessorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfessorViewHolder holder, int position) {
        Professor professor = professors.get(position);
        holder.firstNameTextView.setText(professor.firstName);
        holder.lastNameTextView.setText(professor.lastName);
        holder.moduleNameTextView.setText(professor.moduleName);

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener(v -> deleteProfessor(professor.getId(), position));

        // Set click listener for the update button
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateProfessorActivity.class);
            intent.putExtra("professorId", professor.getId());
            intent.putExtra("professorFirstName", professor.getFirstName());
            intent.putExtra("professorLastName", professor.getLastName());
            intent.putExtra("professorModule", professor.getModuleName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    private void deleteProfessor(Long profID, int position) {
        String url = DELETE_URL + profID;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    professors.remove(position);
                    notifyItemRemoved(position);
                },
                error -> {
                    // Handle error case (e.g., show a message)
                });

        Volley.newRequestQueue(context).add(deleteRequest);
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView;
        TextView lastNameTextView;
        TextView moduleNameTextView;
        Button deleteButton;
        Button updateButton; // Add update button

        public ProfessorViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_professor_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_professor_last_name);
            moduleNameTextView = itemView.findViewById(R.id.text_professor_occupation);
            deleteButton = itemView.findViewById(R.id.btn_delete_professor);
            updateButton = itemView.findViewById(R.id.btn_update_professor); // Initialize update button
        }
    }

    public static class Professor {
        private Long id;
        private String firstName;
        private String lastName;
        private String moduleName;

        public Professor(Long id, String firstName, String lastName, String moduleName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.moduleName = moduleName;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getModuleName() {
            return moduleName;
        }
    }
}
