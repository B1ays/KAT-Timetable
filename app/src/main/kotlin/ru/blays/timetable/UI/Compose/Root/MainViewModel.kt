package ru.blays.timetable.UI.Compose.Root

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

    private val initialSettings = getSettingsUseCase.execut()

    // collapsing app bar state
    private var _favoriteButtonChecked by mutableStateOf(false)
    private var _favoriteButtonVisible by mutableStateOf(false)
    private var _navigateBackButtonVisible by mutableStateOf(false)
    private var _titleText by mutableStateOf("Главная")
    private var _subtitleText by mutableStateOf("")
    private var _subtitleVisible by mutableStateOf(false)

    var favoriteHref by mutableStateOf(initialSettings.favorite)
        private set

    var favoriteSource = initialSettings.favoriteSource
        private set

    val openFavoriteOnAppStart = initialSettings.openFavoriteOnStart ?: false

    // floating menu state
    private var _isMenuExpanded by mutableStateOf(false)

    // theme state
    var isDarkMode by mutableStateOf(true)
        private set

    var systemTheme = true

    var monetColors by mutableStateOf(true)
        private set

    private val initialAccent = AccentColorList.list[initialSettings.accentColor!!]
    var buildedTheme by mutableStateOf(
        buildTheme(
            colorDark = initialAccent.accentDark,
            lightColor = initialAccent.accentLight
        )
    )
        private set

    // Pull refresh state
    var isRefreshing: Boolean by mutableStateOf(false)

    var isPullRefreshAvailable: Boolean by mutableStateOf(false)

    //

    var isInit = true


    // floating menu state change
    var isMenuExpanded: Boolean
        get() = _isMenuExpanded
        set(value) {
            _isMenuExpanded = value
        }


    // collapsing app bar state change
    var favoriteButtonChecked: Boolean
        get() = _favoriteButtonChecked
        set(value) {
            _favoriteButtonChecked = value
        }

    var favoriteButtonVisible: Boolean
        get() = _favoriteButtonVisible
        set(value) {
            _favoriteButtonVisible = value
        }

    var navigateBackButtonVisible: Boolean
        get() = _navigateBackButtonVisible
        set(value) {
            _navigateBackButtonVisible = value
        }

    var titleText: String
        get() = _titleText
        set(value) {
            _titleText = value
        }

    var subtitleText: String
        get() = _subtitleText
        set(value) {
            _subtitleText = value
        }


    var subtitleVisible: Boolean
        get() = _subtitleVisible
        set(value) {
            _subtitleVisible = value
        }


    fun setAsFavorite(href: String, currentSource: Int) {
        favoriteHref = href
        favoriteSource = currentSource
        settingsUseCase.execut(SettingsModel(favorite = href, favoriteSource = currentSource))
    }

    fun init() {
        isDarkMode = when (initialSettings.appTheme) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
        monetColors = initialSettings.monetTheme ?: false
    }

    fun changeTheme(themeCode: Int) {
        when (themeCode) {
            0 -> isDarkMode = systemTheme
            1 -> isDarkMode = true
            2 -> isDarkMode = false
        }
    }

    fun changeMonetUsage(isMonetColors: Boolean) {
        monetColors = isMonetColors
    }

    fun changeAccentColor(index: Int) {
        with(AccentColorList.list[index]) {
            buildedTheme = buildTheme(
                colorDark = accentDark,
                lightColor = accentLight
            )
        }
    }

    var refreshAction : () -> Unit = {}

    fun setParameterForScreen(screenType: String, titleText: String, subtitleText: String = "", pullRefreshAction: () -> Unit = {}, isRefreshing: Boolean = false) {
        when (screenType) {
            "MAIN_SCREEN" -> {
                _favoriteButtonVisible = false
                _navigateBackButtonVisible = false
                subtitleVisible = false
                isPullRefreshAvailable = true
                _titleText = titleText
                _subtitleText = subtitleText
                refreshAction = pullRefreshAction
                this.isRefreshing = isRefreshing
            }
            "TIMETABLE_SCREEN" -> {
                _favoriteButtonVisible = true
                _navigateBackButtonVisible = true
                subtitleVisible = true
                isPullRefreshAvailable = true
                _titleText = titleText
                _subtitleText = subtitleText
                refreshAction = pullRefreshAction
                this.isRefreshing = isRefreshing
            }
            "ABOUT_SCREEN" -> {
                _favoriteButtonVisible = false
                _navigateBackButtonVisible = true
                subtitleVisible = false
                isPullRefreshAvailable = false
                _titleText = titleText
            }
            "SETTINGS_SCREEN" -> {
                _favoriteButtonVisible = false
                _navigateBackButtonVisible = true
                subtitleVisible = false
                isPullRefreshAvailable = false
                _titleText = titleText
            }
        }
    }
}