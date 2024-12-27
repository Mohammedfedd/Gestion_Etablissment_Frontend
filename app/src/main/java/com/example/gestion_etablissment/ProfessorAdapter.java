package com.example.gestion_etablissment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder> {

    private List<Professor> professors;

    public ProfessorAdapter(List<Professor> professors) {
        this.professors = professors;
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
        holder.occupationTextView.setText(professor.occupation);
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView;
        TextView lastNameTextView;
        TextView occupationTextView;

        public ProfessorViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_professor_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_professor_last_name);
            occupationTextView = itemView.findViewById(R.id.text_professor_occupation);
        }
    }

    public static class Professor {
        private String firstName;
        private String lastName;
        private String occupation;

        public Professor(String firstName, String lastName, String occupation) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.occupation = occupation;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getOccupation() {
            return occupation;
        }
    }
}
