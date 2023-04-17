package ru.blays.timetable.UI.Compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository
import ru.blays.timetable.domain.useCases.GetSettingsUseCase

class MainViewModel(
    val mediatingRepository: MediatingRepository,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    // collapsing app bar state
    private var _favoriteButtonChecked by mutableStateOf(false)
    private var _favoriteButtonVisible by mutableStateOf(false)
    private var _navigateBackButtonVisible by mutableStateOf(false)
    private var _titleText by mutableStateOf("Главная")
    private var _subtitleText by mutableStateOf("")
    private var _subtitleVisible by mutableStateOf(false)

    private val initialSettings = getSettingsUseCase.execut()

    // floating menu state
    private var _isMenuExpanded by mutableStateOf(false)

    // theme state
    var isDarkMode by mutableStateOf(false)
    var systemTheme = true
    var monetColors by mutableStateOf(false)

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