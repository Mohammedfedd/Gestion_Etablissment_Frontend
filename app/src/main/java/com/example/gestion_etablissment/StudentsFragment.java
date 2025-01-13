package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {

    private static final String BASE_URL = "http://10.0.2.2:8080/api/etudiants";

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<String> studentFirstNames;
    private List<String> studentLastNames;
    private List<String> studentNiveaux;
    private Button btnAddStudent;
    private List<Etudiant> etudiants;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = view.findViewById(R.id.recyclerStudents);
        btnAddStudent = view.findViewById(R.id.btnAddStudent);
        SearchView searchView = view.findViewById(R.id.search_students);

        studentFirstNames = new ArrayList<>();
        studentLastNames = new ArrayList<>();
        studentNiveaux = new ArrayList<>();
        etudiants = new ArrayList<>();

        studentAdapter = new StudentAdapter(studentFirstNames, studentLastNames, studentNiveaux, position -> {
            String firstName = studentFirstNames.get(position);
            String lastName = studentLastNames.get(position);
            deleteStudent(firstName, lastName);
        }, position -> {
            String firstName = studentFirstNames.get(position);
            String lastName = studentLastNames.get(position);
            navigateToUpdateActivity(firstName, lastName);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(studentAdapter);

        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddStudentActivity.class);
            startActivity(intent);
        });

        // Handle search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterStudents(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterStudents(newText);
                return false;
            }
        });

        return view;
    }

    private void filterStudents(String query) {
        List<String> filteredFirstNames = new ArrayList<>();
        List<String> filteredLastNames = new ArrayList<>();
        List<String> filteredNiveaux = new ArrayList<>();

        for (int i = 0; i < studentFirstNames.size(); i++) {
            String fullName = studentFirstNames.get(i) + " " + studentLastNames.get(i);
            if (fullName.toLowerCase().contains(query.toLowerCase())) {
                filteredFirstNames.add(studentFirstNames.get(i));
                filteredLastNames.add(studentLastNames.get(i));
                filteredNiveaux.add(studentNiveaux.get(i));
            }
        }

        studentAdapter.updateData(filteredFirstNames, filteredLastNames, filteredNiveaux);
    }


    @Override
    public void onResume() {
        super.onResume();
        fetchStudents();
    }

    private void fetchStudents() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL, null,
                response -> {
                    studentFirstNames.clear();
                    studentLastNames.clear();
                    studentNiveaux.clear();
                    etudiants.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject studentObject = response.getJSONObject(i);
                            String firstName = studentObject.getString("prenom");
                            String lastName = studentObject.getString("nom");
                            String studentNiveau = studentObject.getString("niveau");
                            Long studentId = studentObject.getLong("id");

                            Etudiant etudiant = new Etudiant(studentId, firstName, lastName, studentNiveau);
                            etudiants.add(etudiant);

                            studentFirstNames.add(firstName);
                            studentLastNames.add(lastName);
                            studentNiveaux.add(studentNiveau);
                        } catch (JSONException e) {
                            Log.e("StudentsFragment", "Error parsing student JSON: ", e);
                        }
                    }
                    studentAdapter.notifyDataSetChanged();
                },
                error -> Log.e("StudentsFragment", "Error fetching students: " + error.getMessage()));

        requestQueue.add(jsonArrayRequest);
    }

    private void deleteStudent(String firstName, String lastName) {
        Long studentId = getStudentIdByName(firstName, lastName);

        if (studentId != null) {
            String deleteUrl = BASE_URL + "/" + studentId;
            JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, deleteUrl, null,
                    response -> fetchStudents(),
                    error -> Log.e("StudentsFragment", "Error deleting student: " + error.getMessage()));

            Volley.newRequestQueue(getContext()).add(deleteRequest);
        }
    }

    private Long getStudentIdByName(String firstName, String lastName) {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getPrenom().equals(firstName) && etudiant.getNom().equals(lastName)) {
                return etudiant.getId();
            }
        }
        return null;
    }

    private void navigateToUpdateActivity(String firstName, String lastName) {
        Long studentId = getStudentIdByName(firstName, lastName);
        if (studentId != null) {
            Intent intent = new Intent(getContext(), UpdateStudentActivity.class);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
        }
    }

    public static class Etudiant {
        private Long id;
        private String prenom;
        private String nom;
        private String niveau;

        public Etudiant(Long id, String prenom, String nom, String niveau) {
            this.id = id;
            this.prenom = prenom;
            this.nom = nom;
            this.niveau = niveau;
        }

        public Long getId() {
            return id;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getNom() {
            return nom;
        }

        public String getNiveau() {
            return niveau;
        }
    }
}
