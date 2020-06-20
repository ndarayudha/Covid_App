package com.example.appkp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appkp.R
import com.example.appkp.adapter.ViewPagerAdapter
import com.example.appkp.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val images = listOf(
            R.drawable.gambar1,
            R.drawable.gambar2,
            R.drawable.gambar3,
            R.drawable.gambar4
        )

        val title = listOf(
            "Rencanakan Kesehatan",
            "Waspadai Sekitar Anda",
            "Pengguna Sekitar",
            "Cek Kadar O2"
        )

        val description = listOf(
            "Cek secara rutin kesehatan anda di\nlayanan kesehatan terdekat",
            "Gunakan masker saat beraktifitas\ndi dalam maupun diluar ruangan",
            "Ketahui pengguna di sekitar anda",
            "Pantau kadar O2 terlarut dalam darah"
        )


        val adapter = ViewPagerAdapter(
            images,
            title,
            description
        )
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()



        btnRight.setOnClickListener {
            if (btnRight.text.toString() == "Next"){
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                finish()
            }
        }

        btnLeft.setOnClickListener{
            startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
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
                    2 -> {
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
