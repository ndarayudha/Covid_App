package com.example.appkp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.appkp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val handler = Handler().apply {
            postDelayed({
                startActivity(Intent(this@SplashScreenActivity, OnboardingActivity::class.java))
                finish()
            }, 5000)

        }
    }
}
