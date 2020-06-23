package com.example.appkp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appkp.R
import com.example.appkp.ui.auth.presenter.LoginPresenter
import com.example.appkp.ui.auth.view.ILoginView
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class LoginScreenActivity : AppCompatActivity(), ILoginView {


    lateinit var loginPresenter: LoginPresenter
    lateinit var preference: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginPresenter = LoginPresenter(this)
        preference = Preferences(this)


        preference.setValue("firstLaunch", "first")


        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            loginPresenter.onLogin(email, password)
        }
    }



    override fun onLoginSuccess(message: String){
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginError(message: String){
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }
}
