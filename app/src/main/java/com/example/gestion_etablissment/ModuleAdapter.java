package com.example.gestion_etablissment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {

    private List<Module> modules;

    public ModuleAdapter(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModuleViewHolder holder, int position) {
        Module module = modules.get(position);
        holder.nameTextView.setText(module.name);

        holder.deleteButton.setOnClickListener(v -> deleteModule(holder.itemView.getContext(), module, position));

        // Update button functionality
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateModuleActivity.class);
            intent.putExtra("moduleId", module.getId());
            intent.putExtra("moduleName", module.getName());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button deleteButton, updateButton;

        public ModuleViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_module_name);
            deleteButton = itemView.findViewById(R.id.btn_delete_module);
            updateButton = itemView.findViewById(R.id.btn_update_module);
        }
    }

    public static class Module {
        private String name;
        private int id;

        public Module(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private void deleteModule(Context context, Module module, int position) {
        String url = "http://10.0.2.2:8080/api/modules/" + module.getId();

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        modules.remove(position);
                        notifyItemRemoved(position);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
