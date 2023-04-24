package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class SettingsScreenVM(
    private val setSettingsUseCase: SetSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    var settings = get()
        private set

    fun set(settingsModel: SettingsModel) {
        setSettingsUseCase.execut(settingsModel)
    }

    fun get(): SettingsModel {
        return getSettingsUseCase.execut()
    }

    fun changeTheme(themeCode: Int) {
        themeSelectionState = themeCode
        set(SettingsModel(appTheme = themeCode))
    }

    fun changeAccentColor(colorCode: Int) {
        if (colorCode in AccentColorList.list.indices) {
            accentColorIndex = colorCode
            set(SettingsModel(accentColor = colorCode))
        }
    }

    fun changeFirstScreen(openFavoriteOnStart: Boolean) {
        this.openFavoriteOnStart = openFavoriteOnStart
        set(SettingsModel(openFavoriteOnStart = openFavoriteOnStart))
    }

    fun changeMonetUsage(isMonetTheme: Boolean) {
        monetTheme = isMonetTheme
        setSettingsUseCase.execut(SettingsModel(monetTheme = isMonetTheme))
    }

    var themeSelectionState by mutableStateOf(settings.appTheme)

    var accentColorIndex by mutableStateOf(settings.accentColor)

    var monetTheme by mutableStateOf(settings.monetTheme)

    var openFavoriteOnStart by mutableStateOf(settings.openFavoriteOnStart)
}
