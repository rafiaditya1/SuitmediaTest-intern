package com.rafi.suitmediatest.utils

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    val preference: SharedPreferences = context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)

    fun setName(name: String) {
        preference.edit()
            .putString(NAME, name)
            .apply()
    }

    fun setSelectedName(selectedName: String) {
        preference.edit()
            .putString(SELECTED_NAME, selectedName)
            .apply()
    }

    companion object {
        const val LOGIN_SESSION = "login_session"
        const val NAME = "name"
        const val SELECTED_NAME = "selected_name"
    }
}