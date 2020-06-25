package com.example.appkp.ui.auth.presenter


import com.example.appkp.ui.auth.model.User
import com.example.appkp.ui.auth.view.IRegisterView


class RegisterPresenter(
    val registerView: IRegisterView
) : IRegisterPresenter {

    lateinit var user: User


    override fun onRegister(email: String, password: String, name: String): Boolean {
        user = User(email, password, name)
        val loginCode = user.isValidData()

        when (loginCode) {
            0 -> {
                registerView.onRegisterError("You must enter your email")
                return false
            }
            1 -> {
                registerView.onRegisterError("You must enter valid email")
                return false
            }
            2 -> {
                registerView.onRegisterError("Password length must be greater than 6")
                return false
            }
            3 -> {
                registerView.onRegisterError("You must enter your full name")
                return false
            }
            else -> {
                return true
            }
        }
    }

}
