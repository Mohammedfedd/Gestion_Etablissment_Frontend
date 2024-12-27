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
import androidx.appcompat.app.AppCompatActivity

class ProfessorsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfessorAdapter
    private var professorsList = mutableListOf<ProfessorAdapter.Professor>()

    private val ADD_PROFESSOR_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_professors, container, false)

        recyclerView = view.findViewById(R.id.recycler_professors)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ProfessorAdapter(professorsList)
        recyclerView.adapter = adapter

        // Fetch professors data from backend (after implementing)
        fetchProfessorsData()

        val btnAddProfessor: Button = view.findViewById(R.id.btnAddProfessor)
        btnAddProfessor.setOnClickListener {
            val intent = Intent(activity, AddProfessorActivity::class.java)
            startActivityForResult(intent, ADD_PROFESSOR_REQUEST_CODE)
        }

        return view
    }

    private fun fetchProfessorsData() {
        // Fetch professor data from backend (placeholder function)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PROFESSOR_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            val firstName = data?.getStringExtra("firstName")
            val lastName = data?.getStringExtra("lastName")
            val occupation = data?.getStringExtra("occupation")

            if (firstName != null && lastName != null && occupation != null) {
                val newProfessor = ProfessorAdapter.Professor(firstName, lastName, occupation)
                professorsList.add(newProfessor)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
