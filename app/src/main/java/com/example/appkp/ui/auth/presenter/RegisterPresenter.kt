package com.example.appkp.ui.auth.presenter


import com.example.appkp.ui.auth.model.User
import com.example.appkp.ui.auth.view.IResult


class RegisterPresenter(
    val registerView: IResult
) : IRegisterPresenter {

    lateinit var user: User


    override fun onRegister(email: String, password: String, name: String): Boolean {
        user = User(email, password, name)
        val loginCode = user.isValidData()

        when (loginCode) {
            0 -> {
                registerView.onError("You must enter your email")
                return false
            }
            1 -> {
                registerView.onError("You must enter valid email")
                return false
            }
            2 -> {
                registerView.onError("Password length must be greater than 6")
                return false
            }
            3 -> {
                registerView.onError("You must enter your full name")
                return false
            }
            else -> {
                return true
            }
        }
    }

}
