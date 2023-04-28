package ru.blays.timetable.data.repository.preferenceRepository

import android.content.Context
import android.content.SharedPreferences
import ru.blays.timetable.domain.repository.SettingsRepositoryInterface

private const val APP_THEME_TYPE = "AppTheme"
private const val APP_THEME_MONET = "MonetTheme"
private const val APP_THEME_ACCENT = "AccentColor"
private const val FIRST_START = "FirstStart"
private const val FAVORITE_TIMETABLE = "Favorite"
private const val FAVORITE_SOURCE = "FavoriteSource"
private const val OPEN_FAVORITE_ON_START = "StartOnFavorite"
private const val SHOW_TIME_LABEL = "showTimeLabel"


class SettingsRepositoryImpl(context: Context) : SettingsRepositoryInterface {

    private val preferences: SharedPreferences = context.getSharedPreferences("MainPreference", Context.MODE_PRIVATE)

    override var appTheme: Int
        get() = preferences.getInt(APP_THEME_TYPE, 0)
        set(value) = preferences.edit().putInt(APP_THEME_TYPE, value).apply()

    override var monetTheme: Boolean
        get() = preferences.getBoolean(APP_THEME_MONET, true)
        set(value) = preferences.edit().putBoolean(APP_THEME_MONET, value).apply()

    override var accentColor: Int
        get() = preferences.getInt(APP_THEME_ACCENT, 1)
        set(value) = preferences.edit().putInt(APP_THEME_ACCENT, value).apply()

    override var favorite: String
        get() = preferences.getString(FAVORITE_TIMETABLE, "no") ?: "no"
        set(value) = preferences.edit().putString(FAVORITE_TIMETABLE, value).apply()
    override var favoriteSource: Int
        get() = preferences.getInt(FAVORITE_SOURCE, 1)
        set(value) = preferences.edit().putInt(FAVORITE_SOURCE, value).apply()

    override var firstStart: Boolean
        get() = preferences.getBoolean(FIRST_START, false)
        set(value) = preferences.edit().putBoolean(FIRST_START, value).apply()
    override var openFavoriteOnStart: Boolean
        get() = preferences.getBoolean(OPEN_FAVORITE_ON_START, false)
        set(value) = preferences.edit().putBoolean(OPEN_FAVORITE_ON_START, value).apply()

    override var showTimeLabel: Boolean
        get() = preferences.getBoolean(SHOW_TIME_LABEL, false)
        set(value) = preferences.edit().putBoolean(SHOW_TIME_LABEL, value).apply()

}