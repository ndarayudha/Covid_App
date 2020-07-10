package com.example.appkp.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.appkp.R
import com.example.appkp.ui.dashboard.fragment.profil.ProfilFragment
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cardView_edt_profil.setOnClickListener {
            val profilFragment = ProfilFragment()
            val fragment = fragmentManager

            fragment?.beginTransaction()?.apply {
                replace(R.id.nav_host_fragment_container, profilFragment, ProfilFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }


        }
    }

}



