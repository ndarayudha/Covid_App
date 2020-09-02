package com.example.appkp.ui.dashboard.fragment.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.auth.LogoutResponse
import com.example.appkp.ui.auth.LoginScreenActivity
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_option_logout_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OptionLogoutDIalog : DialogFragment() {


    private lateinit var preference: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_logout_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preferences(view.context)

        btn_cancel.setOnClickListener {
            dialog?.cancel()
        }

        btn_logout.setOnClickListener {
            logout()

            val intent = Intent().apply {
                setClass(view.context, LoginScreenActivity::class.java)
                activity?.startActivity(this)
            }

            preference.clear()
            activity?.finishAffinity()
            dialog?.dismiss()
        }
    }


    private fun logout() {
        val token = preference.getValue("token")

        RetrofitBuilder(Constant.BASE_URL).api.logout("Bearer $token")

            .enqueue(object : Callback<LogoutResponse> {

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Log.d("LogoutFailed", t.message)
                }

                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    Log.d("LogoutSuccess", response.body()!!.message)
                }

            })
    }

}
