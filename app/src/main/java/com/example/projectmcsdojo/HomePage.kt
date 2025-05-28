package com.example.projectmcsdojo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.projectmcsdojo.fragments.HomeFragment
import com.example.projectmcsdojo.fragments.HistoryFragment
import com.example.projectmcsdojo.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, 0)
            insets
        }

        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)

        val navBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navBar.itemIconTintList = null

        navBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.icMain -> makeCurrentFragment(homeFragment)
                R.id.icHistory -> makeCurrentFragment(historyFragment)
                R.id.icProfile -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flWrapper, fragment)
        commit()
    }
}

