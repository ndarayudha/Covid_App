package com.example.appkp.ui.auth.presenter

interface IRegisterPresenter {
    fun onRegister(email: String, password: String, name: String) : Boolean
}
