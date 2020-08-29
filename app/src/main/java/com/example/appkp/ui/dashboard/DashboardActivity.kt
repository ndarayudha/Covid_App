package com.example.appkp.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appkp.R
import com.example.appkp.util.PermissionManager
import com.example.appkp.util.Preferences
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Attributes

class DashboardActivity : AppCompatActivity() {


    companion object {
        const val ACCESS_COARSE_LOCATION_CODE = 50
    }


    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        preferences = Preferences(this)

        preferences.setValue("inDashboard", "true")


        val navController = findNavController(R.id.nav_host_fragment_container)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav?.setupWithNavController(navController)

        // check permission
        PermissionManager.check(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            ACCESS_COARSE_LOCATION_CODE
        )
    }

}
