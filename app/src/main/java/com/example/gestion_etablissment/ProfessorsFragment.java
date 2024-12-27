package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProfessorAdapter adapter;
    private List<ProfessorAdapter.Professor> professorsList = new ArrayList<>();

    private static final int ADD_PROFESSOR_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professors, container, false);

        recyclerView = view.findViewById(R.id.recycler_professors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ProfessorAdapter(professorsList);
        recyclerView.setAdapter(adapter);

        fetchProfessorsData();

        Button btnAddProfessor = view.findViewById(R.id.btnAddProfessor);
        btnAddProfessor.setOnClickListener(v -> {
        Intent intent = new Intent(getActivity(), AddProfessorActivity.class);
        startActivityForResult(intent, ADD_PROFESSOR_REQUEST_CODE);
    });

        return view;
    }

    private void fetchProfessorsData() {
        // Fetch professor data from backend (placeholder function)
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PROFESSOR_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            String firstName = data.getStringExtra("firstName");
            String lastName = data.getStringExtra("lastName");
            String occupation = data.getStringExtra("occupation");

            if (firstName != null && lastName != null && occupation != null) {
                ProfessorAdapter.Professor newProfessor = new ProfessorAdapter.Professor(firstName, lastName, occupation);
                professorsList.add(newProfessor);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
