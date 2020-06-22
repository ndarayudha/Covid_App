package com.example.appkp.ui.auth.presenter

import com.example.appkp.ui.auth.model.User
import com.example.appkp.ui.auth.view.ILoginView

class LoginPresenter(val loginView: ILoginView) : ILoginPresenter {

    lateinit var user: User

    override fun onLogin(email: String, password: String){

        user = User(email, password)
        val loginCode = user.isValidData()

        when (loginCode) {
            0 -> {
                loginView.onLoginError("You must enter your email")
            }
            1 -> {
                loginView.onLoginError("You must enter valid email")
            }
            2 -> {
                loginView.onLoginError("Password length must be greater than 6")
            }
            else -> {
                loginView.onLoginSuccess("Login success")
            }
        }
    }
}
