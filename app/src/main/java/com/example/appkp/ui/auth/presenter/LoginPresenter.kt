package com.example.appkp.ui.auth.presenter


import com.example.appkp.ui.auth.model.User
import com.example.appkp.ui.auth.view.IResult


class LoginPresenter(
    val loginView: IResult
) : ILoginPresenter {

    lateinit var user: User

    override fun onLogin(email: String, password: String) : Boolean{

        user = User(email, password)
        val loginCode = user.isValidData()

        when (loginCode) {
            0 -> {
                loginView.onError("You must enter your email")
                return false
            }
            1 -> {
                loginView.onError("You must enter valid email")
                return false
            }
            2 -> {
                loginView.onError("Password length must be greater than 6")
                return false
            }
            else -> {
                return true
            }
        }
    }

}
