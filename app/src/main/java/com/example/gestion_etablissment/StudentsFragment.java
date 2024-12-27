package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<StudentAdapter.Student> studentsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = view.findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new StudentAdapter(studentsList, Collections.emptyList());
        recyclerView.setAdapter(adapter);

        Button buttonAddStudent = view.findViewById(R.id.btnAddStudent);
        buttonAddStudent.setOnClickListener(v -> {
        Intent intent = new Intent(getContext(), AddStudentActivity.class);
        startActivity(intent);
    });

        return view;
    }
}
