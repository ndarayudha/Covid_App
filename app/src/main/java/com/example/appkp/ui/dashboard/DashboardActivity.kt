package com.example.appkp.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appkp.R
import java.util.prefs.Preferences

class DashboardActivity : AppCompatActivity() {

    lateinit var preferences: com.example.appkp.util.Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        preferences = com.example.appkp.util.Preferences(this)

        preferences.setValue("inDashboard", "true")
    }
}
