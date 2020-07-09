package com.example.appkp.ui.dashboard.fragment

import android.app.Instrumentation
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appkp.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.reflect.KClass

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        progressBar.apply{
//            progressMax = 100f
//            setProgressWithAnimation(50f, 1000)
//            progressBarWidth = 5f
//            backgroundProgressBarWidth = 7f
//            progressBarColor = Color.GREEN
//        }
//    }


    }





