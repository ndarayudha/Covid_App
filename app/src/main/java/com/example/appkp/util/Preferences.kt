package com.example.appkp.util

import android.content.Context

class Preferences(context: Context){

    companion object {
        const val PRIVATE_MODE = 0
        const val PREF = "COVID_PREF"
    }

    val sharePref = context.getSharedPreferences(PREF, PRIVATE_MODE)


    private fun setValue(key: String, value: String){
        val editor = sharePref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getValue(key: String) : String? {
        return sharePref.getString(key, "")
    }
}
