package com.example.appkp.ui.auth.model

import android.text.TextUtils
import android.util.Patterns

class User(
    var email: String,
    var password: String
) : IUser {


    override fun getUserEmail(): String {
        return email
    }

    override fun getUserPassword(): String {
        return password
    }

    override fun isValidData() : Int {

        return if(TextUtils.isEmpty(getUserEmail())) {
            0
        } else if(!Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches()) {
            1
        } else if(getUserPassword().length <= 6) {
            2
        } else {
            -1
        }
    }
}
