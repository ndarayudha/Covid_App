package com.example.appkp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.example.appkp.R
import com.example.appkp.ui.dashboard.DashboardActivity
import com.example.appkp.util.Preferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        preference = Preferences(this)


        lifecycleScope.launch {
            delay(5000L)


            when {
                preference.getValue("isLoggedIn").equals("true") -> {
                    startActivity(Intent(this@SplashScreenActivity, PhotoScreenActivity::class.java))
                    finish()
                }
                else -> {
                    startActivity(Intent(this@SplashScreenActivity, OnboardingActivity::class.java))
                    finish()
                }
            }

        }
    }
}
