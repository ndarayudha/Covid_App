package com.example.appkp.ui.auth.presenter

interface ILoginPresenter {
    fun onLogin(email: String, password: String) : Boolean
}
