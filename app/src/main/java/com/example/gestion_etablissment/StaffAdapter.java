package com.example.gestion_etablissment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private List<Staff> staffList;

    public StaffAdapter(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @Override
    public StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.firstNameTextView.setText(staff.getFirstName());
        holder.lastNameTextView.setText(staff.getLastName());
        holder.occupationTextView.setText(staff.getOccupation());
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView;
        TextView lastNameTextView;
        TextView occupationTextView;

        public StaffViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_staff_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_staff_last_name);
            occupationTextView = itemView.findViewById(R.id.text_staff_occupation);
        }
    }

    public static class Staff {
        private String firstName;
        private String lastName;
        private String occupation;

        public Staff(String firstName, String lastName, String occupation) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.occupation = occupation;
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
