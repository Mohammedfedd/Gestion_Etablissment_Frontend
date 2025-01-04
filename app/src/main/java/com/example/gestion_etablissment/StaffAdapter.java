package com.example.gestion_etablissment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private List<Staff> staffList;
    private Context context;

    public StaffAdapter(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @Override
    public StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff, parent, false);
        context = parent.getContext();
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.firstNameTextView.setText(staff.getFirstName());
        holder.lastNameTextView.setText(staff.getLastName());
        holder.occupationTextView.setText(staff.getOccupation());

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteStaff(staff.getId(), position));

        // Update button click listener
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateStaffActivity.class);
            intent.putExtra("id", staff.getId());
            intent.putExtra("firstName", staff.getFirstName());
            intent.putExtra("lastName", staff.getLastName());
            intent.putExtra("occupation", staff.getOccupation());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView, lastNameTextView, occupationTextView;
        Button deleteButton, updateButton;

        public StaffViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_staff_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_staff_last_name);
            occupationTextView = itemView.findViewById(R.id.text_staff_occupation);
            deleteButton = itemView.findViewById(R.id.btn_delete_staff);
            updateButton = itemView.findViewById(R.id.btn_update_staff);
        }
    }

    private void deleteStaff(Long staffId, int position) {
        String url = "http://10.0.2.2:8080/api/personnel/" + staffId;

        // Send DELETE request to the server
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    staffList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Staff Deleted", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(context, "Error deleting staff", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(context).add(deleteRequest);
    }

    public static class Staff {
        private Long id;
        private String firstName, lastName, occupation;

        public Staff(Long id, String firstName, String lastName, String occupation) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.occupation = occupation;
        }

        public Long getId() {
            return id;
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
