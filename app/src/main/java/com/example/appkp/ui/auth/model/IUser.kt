package com.example.appkp.ui.auth.model

interface IUser {

    fun getUserEmail() : String?
    fun getUserPassword() : String?
    fun getName(): String?
    fun isValidData() : Int
}
