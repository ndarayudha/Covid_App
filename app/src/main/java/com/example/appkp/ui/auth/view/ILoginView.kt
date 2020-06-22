package com.example.appkp.ui.auth.view

interface ILoginView {
    fun onLoginSuccess(message: String)
    fun onLoginError(message: String)
}
