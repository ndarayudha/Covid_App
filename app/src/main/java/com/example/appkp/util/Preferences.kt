package com.example.appkp.util

import android.content.Context

class Preferences(context: Context){

    companion object {
        const val PRIVATE_MODE = 0
        const val PREF = "COVID_PREF"
    }

    val sharePref = context.getSharedPreferences(PREF, PRIVATE_MODE)


    fun setValue(key: String, value: String){
        val editor = sharePref.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getValue(key: String) : String? {
        return sharePref.getString(key, "")
    }

    fun removeValue(key:String){
        sharePref.edit().remove(key).apply()
    }

    fun clear() {
        sharePref.edit().clear().apply()
    }
}
