package com.example.gestion_etablissment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        Button btnAddStaff = view.findViewById(R.id.btn_add_staff);
        btnAddStaff.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddStaffActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchStaffData();
    }

    private void fetchStaffData() {
        String url = "http://10.0.2.2:8080/api/personnel";

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    staffList.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject staffJson = response.getJSONObject(i);
                            Long staffId = staffJson.getLong("id");
                            String firstName = staffJson.getString("prenom");
                            String lastName = staffJson.getString("nom");
                            String occupation = staffJson.getString("occupation");


                            StaffAdapter.Staff staff = new StaffAdapter.Staff(staffId, firstName, lastName, occupation);
                            staffList.add(staff);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error parsing staff data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Failed to fetch staff data", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
