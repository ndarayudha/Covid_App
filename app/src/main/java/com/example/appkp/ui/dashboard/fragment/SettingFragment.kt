package com.example.appkp.ui.dashboard.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.auth.LogoutResponse
import com.example.appkp.ui.auth.LoginScreenActivity
import com.example.appkp.ui.dashboard.DashboardActivity
import com.example.appkp.ui.dashboard.fragment.dialog.OptionLogoutDIalog
import com.example.appkp.ui.dashboard.fragment.profil.ProfilFragment
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingFragment : Fragment() {


    private lateinit var preference: Preferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preferences(view.context)



        cardView_edt_profil.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_profilFragment)
        }

        cardView_Logout.setOnClickListener {
            val optionLogoutDialog = OptionLogoutDIalog()
            val mFragmentManager = parentFragmentManager
            optionLogoutDialog.show(mFragmentManager, OptionLogoutDIalog::class.java.simpleName)
        }
    }

}



