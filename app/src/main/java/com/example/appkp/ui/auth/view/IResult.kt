package com.example.appkp.ui.auth.view

interface IResult {
    fun onSuccess(message: String)
    fun onError(message: String)
}
