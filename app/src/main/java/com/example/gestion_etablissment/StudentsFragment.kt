package com.example.gestion_etablissment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private var studentsList = mutableListOf<StudentAdapter.Student>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)

        recyclerView = view.findViewById(R.id.recycler_students)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = StudentAdapter(studentsList, listOf())
        recyclerView.adapter = adapter

        val buttonAddStudent: Button = view.findViewById(R.id.btnAddStudent)
        buttonAddStudent.setOnClickListener {
            val intent = Intent(context, AddStudentActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
