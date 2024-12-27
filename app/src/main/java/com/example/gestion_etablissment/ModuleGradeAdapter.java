package com.example.gestion_etablissment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleGradeAdapter extends RecyclerView.Adapter<ModuleGradeAdapter.ModuleGradeViewHolder> {

    private List<String> modules;
    private Map<String, String> grades = new HashMap<>();

    public ModuleGradeAdapter(List<String> modules) {
        this.modules = modules;
    }

    @Override
    public ModuleGradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module_grade, parent, false);
        return new ModuleGradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModuleGradeViewHolder holder, int position) {
        String module = modules.get(position);
        holder.moduleNameTextView.setText(module);

        holder.gradeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                grades.put(module, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public Map<String, String> getGrades() {
        return grades;
    }

    public static class ModuleGradeViewHolder extends RecyclerView.ViewHolder {
        TextView moduleNameTextView;
        EditText gradeEditText;

        public ModuleGradeViewHolder(View itemView) {
            super(itemView);
            moduleNameTextView = itemView.findViewById(R.id.textModuleName);
            gradeEditText = itemView.findViewById(R.id.editTextGrade);
        }
    }
}
