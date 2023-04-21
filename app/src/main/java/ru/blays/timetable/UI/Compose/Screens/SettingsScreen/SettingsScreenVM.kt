package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.blays.AppUpdater.UpdateChecker
import ru.blays.AppUpdater.dataClasses.GetInfoResult
import ru.blays.AppUpdater.web.api.HttpClient
import ru.blays.AppUpdater.web.api.JsonSerializer
import ru.blays.timetable.BuildConfig
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class SettingsScreenVM(
    private val setSettingsUseCase: SetSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private var settings = get()


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

    fun changeMonetUsage(isMonetTheme: Boolean) {
        monetTheme = isMonetTheme
        setSettingsUseCase.execut(SettingsModel(monetTheme = isMonetTheme))
    }

    var themeSelectionState by mutableStateOf(settings.appTheme)

    var accentColorIndex by mutableStateOf(settings.accentColor)

    var monetTheme by mutableStateOf(settings.monetTheme)

    var versionName by mutableStateOf("")
    var versionCode by mutableStateOf(0)
    var changed by mutableStateOf("")
    var added by mutableStateOf("")
    var deleted by mutableStateOf("")

    private var url = ""

    var isUpdateAvailable by mutableStateOf(false)

    var isChangelogShowed by mutableStateOf(false)

    fun downloadNewVersion(context: Context) {
        UpdateChecker(context).run {
            downloadAndInstall(url)
        }
    }

    suspend fun updateCheck() = withContext(Dispatchers.IO) {

        val result: GetInfoResult

        val httpClient = HttpClient()
        result = httpClient.get()
        Log.d("HTTP_request_log", result.json ?: "Json is null")

        if (result.status) {
            try {


                val jsonSerializer = JsonSerializer()
                val updateInfo = jsonSerializer.fromJsonToClass(result.json ?: "")
                Log.d(
                    "serializationLog", updateInfo.toString()
                )
                if (updateInfo != null) {
                    versionName = updateInfo.versionName
                    versionCode = updateInfo.versionCode
                    changed = updateInfo.changed
                    added = updateInfo.added
                    deleted = updateInfo.deleted
                    url = updateInfo.url
                }
                if (versionCode > BuildConfig.VERSION_CODE) isUpdateAvailable = true
            } catch (e: Exception) {
                Log.d(
                "serializationLog",  "$e"
            ) }
        }
    }
}
