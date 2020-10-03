package com.hr.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import life.shank.android.AutoScoped

class MainActivity : AppCompatActivity(), AutoScoped {

    lateinit var controller: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(
            navGraphIds = listOf(R.navigation.list, R.navigation.grid),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
    }
}