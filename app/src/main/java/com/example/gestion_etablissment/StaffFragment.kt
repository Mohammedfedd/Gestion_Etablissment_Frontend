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

class StaffFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StaffAdapter
    private var staffList = mutableListOf<StaffAdapter.Staff>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff, container, false)

        recyclerView = view.findViewById(R.id.recycler_staff)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = StaffAdapter(staffList)
        recyclerView.adapter = adapter

        fetchStaffData()

        val btnAddStaff: Button = view.findViewById(R.id.btn_add_staff)
        btnAddStaff.setOnClickListener {
            val intent = Intent(activity, AddStaffActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun fetchStaffData() {
        // Implement data fetching logic for staff
    }
}
