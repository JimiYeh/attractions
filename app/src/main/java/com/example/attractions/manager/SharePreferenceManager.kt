package com.example.attractions.manager

import android.content.Context
import android.content.SharedPreferences

object SharePreferenceManager {
    private const val PREF_NAME = "attractions_preferences"
    private const val KEY_LANGUAGE = "language"
    private const val DEFAULT_LANGUAGE = "zh-tw"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var language: String
        get() = preferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(value) = preferences.edit().putString(KEY_LANGUAGE, value).apply()
}