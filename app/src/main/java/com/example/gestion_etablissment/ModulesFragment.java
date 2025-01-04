package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModulesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ModuleAdapter adapter;
    private List<ModuleAdapter.Module> moduleList = new ArrayList<>();
    private List<ModuleAdapter.Module> filteredModuleList = new ArrayList<>(); // To hold the filtered modules

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modules, container, false);

        recyclerView = view.findViewById(R.id.recycler_modules);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ModuleAdapter(filteredModuleList); // Use filtered list for adapter
        recyclerView.setAdapter(adapter);

        Button btnAddModule = view.findViewById(R.id.btn_add_module);
        btnAddModule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddModuleActivity.class);
            startActivity(intent);
        });

        // Initialize SearchView
        SearchView searchView = view.findViewById(R.id.search_modules);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterModules(newText); // Filter modules based on search query
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchModuleData(); // Fetch the data when the fragment is resumed
    }

    private void fetchModuleData() {
        String url = "http://10.0.2.2:8080/api/modules";  // Replace with your API endpoint

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        moduleList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject moduleObject = response.getJSONObject(i);
                                String moduleName = moduleObject.getString("name");
                                int moduleId = moduleObject.getInt("id");

                                ModuleAdapter.Module module = new ModuleAdapter.Module(moduleId, moduleName);
                                module.setId(moduleId); // Set the ID
                                moduleList.add(module);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Initially display all modules
                        filteredModuleList.clear();
                        filteredModuleList.addAll(moduleList);
                        adapter.notifyDataSetChanged(); // Notify adapter of new data
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ModulesFragment", "Error fetching modules: " + error.getMessage());
                    }
                }
        );
        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
    }

    // Filter modules based on the search query
    private void filterModules(String query) {
        filteredModuleList.clear();
        if (query.isEmpty()) {
            filteredModuleList.addAll(moduleList); // Show all if no query is entered
        } else {
            for (ModuleAdapter.Module module : moduleList) {
                if (module.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredModuleList.add(module);
                }
            }
        }
        adapter.notifyDataSetChanged(); // Update the RecyclerView
    }
}
