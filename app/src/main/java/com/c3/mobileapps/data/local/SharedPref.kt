package com.c3.mobileapps.data.local

import android.content.Context

class SharedPref(context: Context) {

    private val sharedPreference = "userPrefer"
    private var preferences = context.getSharedPreferences(sharedPreference, 0)
    private val editor = preferences.edit()

    fun setIsLogin(value: Boolean) {
        editor.putBoolean(IS_LOGIN, value).apply()
    }

    fun getIsLogin(): Boolean {
        return preferences.getBoolean(IS_LOGIN, false)
    }

    fun setToken(value: String) {
        editor.putString(TOKEN, value).apply()
    }

    fun getToken(): String {
        return preferences.getString(TOKEN,"Bearer ").toString()
    }

    fun clearPreferences(){
        editor.clear()
        editor.commit()
    }

    companion object{
        const val IS_LOGIN = "islogin"
        const val TOKEN = "token"
    }
}