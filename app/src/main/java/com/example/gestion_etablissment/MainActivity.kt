package com.example.gestion_etablissment

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, StudentsFragment())
                .commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_students -> selectedFragment = StudentsFragment()
                R.id.nav_professors -> selectedFragment = ProfessorsFragment()
                R.id.nav_staff -> selectedFragment = StaffFragment()
                R.id.nav_modules -> selectedFragment = ModulesFragment()
            }

            if (selectedFragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .commit()
            }
            true
        }
    }
}