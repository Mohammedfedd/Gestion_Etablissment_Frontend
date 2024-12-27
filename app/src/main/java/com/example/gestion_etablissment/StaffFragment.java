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

public class StaffFragment extends Fragment {

    private RecyclerView recyclerView;
    private StaffAdapter adapter;
    private List<StaffAdapter.Staff> staffList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        recyclerView = view.findViewById(R.id.recycler_staff);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new StaffAdapter(staffList);
        recyclerView.setAdapter(adapter);

        fetchStaffData();

        Button btnAddStaff = view.findViewById(R.id.btn_add_staff);
        btnAddStaff.setOnClickListener(v -> {
        Intent intent = new Intent(getActivity(), AddStaffActivity.class);
        startActivity(intent);
    });

        return view;
    }

    private void fetchStaffData() {
        // Implement data fetching logic for staff
    }
}
