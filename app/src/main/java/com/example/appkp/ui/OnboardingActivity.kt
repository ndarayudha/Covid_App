package com.example.appkp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appkp.R
import com.example.appkp.adapter.ViewPagerAdapter
import com.example.appkp.ui.auth.LoginScreenActivity
import com.example.appkp.util.Preferences
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {


    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        preferences = Preferences(this)


        if (preferences.getValue("firstLaunch").equals("first")){
            startActivity(Intent(this, LoginScreenActivity::class.java))
            finish()
        }


        val images = resources.obtainTypedArray(R.array.onboardingImages)
        val title = resources.getStringArray(R.array.onboardingTitle)
        val description = resources.getStringArray(R.array.onboardingDescription)


        val adapter = ViewPagerAdapter(images, title, description)
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()



        btnRight.setOnClickListener {
            if (btnRight.text.toString() == "Next") {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                startActivity(Intent(this@OnboardingActivity, LoginScreenActivity::class.java))
                finish()
            }
        }

        btnLeft.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, LoginScreenActivity::class.java))
            finish()
        }



        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val currentTabPosition = tab?.position

                when (currentTabPosition) {
                    0 -> {
                        btnLeft.visibility = View.VISIBLE
                        btnLeft.isEnabled = true
                        btnRight.isEnabled = true
                        btnRight.text = "Next"
                    }
                    1 -> {
                        btnLeft.visibility = View.GONE
                        btnLeft.isEnabled = false
                        btnRight.text = "Next"
                    }
                    else -> {
                        btnLeft.visibility = View.GONE
                        btnLeft.isEnabled = false
                        btnRight.text = "Finish"
                    }
                }
            }
        })
    }
}
