package com.example.gestion_etablissment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<String> studentFirstNames;
    private List<String> studentLastNames;
    private List<String> studentNiveaux;
    private OnDeleteClickListener onDeleteClickListener;
    private OnUpdateClickListener onUpdateClickListener;

    public StudentAdapter(List<String> studentFirstNames, List<String> studentLastNames, List<String> studentNiveaux,
                          OnDeleteClickListener onDeleteClickListener, OnUpdateClickListener onUpdateClickListener) {
        this.studentFirstNames = studentFirstNames;
        this.studentLastNames = studentLastNames;
        this.studentNiveaux = studentNiveaux;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String firstName = studentFirstNames.get(position);
        String lastName = studentLastNames.get(position);
        String studentNiveau = studentNiveaux.get(position);

        holder.textFirstName.setText(firstName);
        holder.textLastName.setText(lastName);
        holder.textStudentNiveau.setText("Niveau: " + studentNiveau);

        // Set the delete button functionality
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(position));

        // Set the update button functionality
        holder.updateButton.setOnClickListener(v -> onUpdateClickListener.onUpdateClick(position));
    }

    @Override
    public int getItemCount() {
        return studentFirstNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFirstName;
        TextView textLastName;
        TextView textStudentNiveau;
        Button deleteButton;
        Button updateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstName = itemView.findViewById(R.id.textFirstName);
            textLastName = itemView.findViewById(R.id.textLastName);
            textStudentNiveau = itemView.findViewById(R.id.textStudentNiveau);
            deleteButton = itemView.findViewById(R.id.btnDeleteStudent);
            updateButton = itemView.findViewById(R.id.btnUpdateStudent);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnUpdateClickListener {
        void onUpdateClick(int position);
    }

    /**
     * Updates the data of the adapter and refreshes the view.
     *
     * @param firstNames List of updated first names.
     * @param lastNames  List of updated last names.
     * @param niveaux    List of updated levels.
     */
    public void updateData(List<String> firstNames, List<String> lastNames, List<String> niveaux) {
        this.studentFirstNames = firstNames;
        this.studentLastNames = lastNames;
        this.studentNiveaux = niveaux;
        notifyDataSetChanged();
    }
}
