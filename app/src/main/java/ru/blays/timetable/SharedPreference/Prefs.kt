package ru.blays.timetable.SharedPreference

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MainPreference", Context.MODE_PRIVATE)

    private val APP_THEME_TYPE = "AppTheme"
    private val APP_THEME_MONET = "MonetTheme"
    private val APP_THEME_ACCENT = "AccentColor"
    private val FAVORITE_TIMETABLE = "Favorite"
    private val FIRST_START = "FirstStart"

    var themePrefs: Int
        get() = preferences.getInt(APP_THEME_TYPE, 0)
        set(value) = preferences.edit().putInt(APP_THEME_TYPE, value).apply()

    var monetPrefs: Boolean
        get() = preferences.getBoolean(APP_THEME_MONET, true)
        set(value) = preferences.edit().putBoolean(APP_THEME_MONET, value).apply()

    var accentColorPrefs: Int
        get() = preferences.getInt(APP_THEME_ACCENT, 1)
        set(value) = preferences.edit().putInt(APP_THEME_ACCENT, value).apply()

    var favoriteTimetablePrefs: String?
        get() = preferences.getString(FAVORITE_TIMETABLE, "no")
        set(value) = preferences.edit().putString(FAVORITE_TIMETABLE, value).apply()


    var firstStart: Boolean
        get() = preferences.getBoolean(FIRST_START, false)
        set(value) = preferences.edit().putBoolean(FIRST_START, value).apply()
}