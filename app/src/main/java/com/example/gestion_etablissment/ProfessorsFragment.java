package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProfessorAdapter adapter;
    private List<ProfessorAdapter.Professor> professorsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professors, container, false);

        recyclerView = view.findViewById(R.id.recycler_professors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with the list of professors
        adapter = new ProfessorAdapter(professorsList, getContext());
        recyclerView.setAdapter(adapter);

        // Add Professor button
        Button btnAddProfessor = view.findViewById(R.id.btnAddProfessor);
        btnAddProfessor.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddProfessorActivity.class);
            startActivity(intent); // Navigate to AddProfessorActivity
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchProfessorsData(); // Refresh data whenever the fragment is visible
    }

    private void fetchProfessorsData() {
        String url = "http://10.0.2.2:8080/api/professeurs";  // Update URL for your endpoint

        // Fetch professor data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    professorsList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject professorObject = response.getJSONObject(i);
                            Long profID = professorObject.getLong("id");
                            String firstName = professorObject.getString("prenom");
                            String lastName = professorObject.getString("nom");
                            String moduleName = professorObject.getString("moduleName");

                            // Add professor to the list
                            ProfessorAdapter.Professor professor = new ProfessorAdapter.Professor(profID, firstName, lastName, moduleName);
                            professorsList.add(professor);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error parsing professor data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(getContext(), "Error fetching professors data", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
}

