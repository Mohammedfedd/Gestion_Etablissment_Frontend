package com.example.gestion_etablissment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class ModulesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModuleAdapter
    private var moduleList = mutableListOf<ModuleAdapter.Module>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modules, container, false)

        recyclerView = view.findViewById(R.id.recycler_modules)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ModuleAdapter(moduleList)
        recyclerView.adapter = adapter

        fetchModuleData()

        val btnAddModule: Button = view.findViewById(R.id.btn_add_module)
        btnAddModule.setOnClickListener {
            val intent = Intent(activity, AddModuleActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun fetchModuleData() {
        // Implement data fetching logic for modules
    }
}
