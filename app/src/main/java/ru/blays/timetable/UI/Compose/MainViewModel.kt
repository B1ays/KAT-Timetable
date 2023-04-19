package ru.blays.timetable.UI.Compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.UI.DataClasses.buildTheme
import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class MainViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val settingsUseCase: SetSettingsUseCase
) : ViewModel() {

    val initialSettings = getSettingsUseCase.execut()

    // collapsing app bar state
    private var _favoriteButtonChecked by mutableStateOf(false)
    private var _favoriteButtonVisible by mutableStateOf(false)
    private var _navigateBackButtonVisible by mutableStateOf(false)
    private var _titleText by mutableStateOf("Главная")
    private var _subtitleText by mutableStateOf("")
    private var _subtitleVisible by mutableStateOf(false)

    val favoriteHref by mutableStateOf(initialSettings.favorite)

    // floating menu state
    private var _isMenuExpanded by mutableStateOf(false)

    // theme state
    var isDarkMode by mutableStateOf(false)
    var systemTheme = true
    var monetColors by mutableStateOf(false)

    private val initialAccent = AccentColorList.list[initialSettings.accentColor!!]
    var buildedTheme by mutableStateOf(buildTheme(colorDark = initialAccent.accentDark, lightColor = initialAccent.accentLight))

    // floating menu state change
    var isMenuExpanded: Boolean
        get() = _isMenuExpanded
        set(value) { _isMenuExpanded = value }


    // collapsing app bar state change
    var favoriteButtonChecked : Boolean
        get() = _favoriteButtonChecked
        set(value) { _favoriteButtonChecked = value }

    var favoriteButtonVisible : Boolean
        get() = _favoriteButtonVisible
        set(value) { _favoriteButtonVisible = value }

    var navigateBackButtonVisible : Boolean
        get() = _navigateBackButtonVisible
        set(value) { _navigateBackButtonVisible = value }

    var titleText : String
        get() = _titleText
        set(value) { _titleText = value }

    var subtitleText: String
        get() = _subtitleText
        set(value) { _subtitleText = value }


    var subtitleVisible : Boolean
        get() = _subtitleVisible
        set(value) { _subtitleVisible = value }

    fun setAsFavorite(href: String) {
        settingsUseCase.execut(SettingsModel(favorite = href))
    }


    fun init() {
        isDarkMode = when(initialSettings.appTheme) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
        monetColors = initialSettings.monetTheme ?: false
    }
}