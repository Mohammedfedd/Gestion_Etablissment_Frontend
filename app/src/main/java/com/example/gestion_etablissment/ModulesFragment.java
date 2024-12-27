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
import java.util.List;

public class ModulesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ModuleAdapter adapter;
    private List<ModuleAdapter.Module> moduleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modules, container, false);

        recyclerView = view.findViewById(R.id.recycler_modules);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ModuleAdapter(moduleList);
        recyclerView.setAdapter(adapter);

        fetchModuleData();

        Button btnAddModule = view.findViewById(R.id.btn_add_module);
        btnAddModule.setOnClickListener(v -> {
        Intent intent = new Intent(getActivity(), AddModuleActivity.class);
        startActivity(intent);
    });

        return view;
    }

    private void fetchModuleData() {
        // Implement data fetching logic for modules
    }
}
