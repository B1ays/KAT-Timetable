package ru.blays.timetable.UI.Compose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.theapache64.rebugger.Rebugger
import io.objectbox.BoxStore
import ru.blays.timetable.UI.Compose.ComposeElements.UpdateInfo
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVMFactory
import ru.blays.timetable.UI.Compose.MainActivity.ObjectBox.objectBoxManager
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListVMFactory
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVMFactory
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableVMFactory
import ru.blays.timetable.UI.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.UI.ScreenData
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.RootElements
import ru.blays.timetable.UI.TimetableKey

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    internal object ObjectBox {
        lateinit var objectBoxManager: BoxStore
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
        this,
        MainViewModelFactory(this)
        )[MainViewModel::class.java]
    }

    private val timetableViewModel by lazy {
        ViewModelProvider(
        this,
        TimetableVMFactory(this, objectBoxManager)
        )[TimetableScreenVM::class.java]
    }

    private val groupListViewModel by lazy {
        ViewModelProvider(
        this,
        GroupListVMFactory(this, objectBoxManager)
        )[SimpleListScreenVM::class.java]
    }

    private val navigationViewModel by lazy {
        ViewModelProvider(
        this,
        NavigationVMFactory()
        )[NavigationVM::class.java]
    }

    private val settingsViewModel by lazy {
        ViewModelProvider(
        this,
        SettingsScreenVMFactory(this)
        )[SettingsScreenVM::class.java]
    }

    private val updateDialog = UpdateInfo(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (actionBar != null) {
            actionBar!!.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkPermissions(
                arrayOf(
                    Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), 1234
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkPermissions(
                arrayOf(
                    Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1234)
        }

        setContent {
            InitApp(
                mainViewModel,
                timetableViewModel,
                groupListViewModel,
                navigationViewModel,
                settingsViewModel
            )

            AviakatTimetableTheme(
                darkTheme = mainViewModel.isDarkMode,
                dynamicColor = mainViewModel.monetColors,
                mainViewModel.buildedTheme
            ) {
                mainViewModel.systemTheme = isSystemInDarkTheme()
                mainViewModel.init()
                RootElements(
                    mainViewModel,
                    timetableViewModel,
                    groupListViewModel,
                    navigationViewModel,
                    settingsViewModel,
                    updateDialog
                )
            }
            Rebugger(composableName = "MainActivity", trackMap = mapOf(
                "mainViewModel" to mainViewModel,
                "timetableViewModel" to timetableViewModel,
                "groupListViewModel" to groupListViewModel,
                "navigationViewModel" to navigationViewModel,
                "settingsViewModel" to settingsViewModel,
                "updateDialog" to updateDialog
            ))
        }
    }

    private fun checkPermissions(permission: Array<String>, requestCode: Int) {
        val needToRequest = mutableListOf<String>()
        permission.forEach {
            if (this.checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            needToRequest.add(it)
            }
        }
        this.requestPermissions(needToRequest.toTypedArray(), requestCode)
    }
}

@Composable
fun InitApp(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: SimpleListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    if (mainViewModel.isInit) {

        with(mainViewModel) {
            systemTheme = isSystemInDarkTheme()
            isDarkMode = when (initialSettings.appTheme) {
                0 -> systemTheme
                1 -> true
                2 -> false
                else -> systemTheme
            }

            monetColors = initialSettings.monetTheme ?: true

            if (mainViewModel.initialSettings.openFavoriteOnStart == true && mainViewModel.favoriteHref != null && mainViewModel.favoriteHref != "no") {
                navigationViewModel.changeScreen(
                    ScreenData(
                        ScreenList.TIMETABLE_SCREEN,
                        TimetableKey(0, mainViewModel.favoriteHref!!)
                    )
                )
            }
            Rebugger(trackMap = mapOf(
                "systemTheme" to systemTheme,
                "monetColors" to monetColors,
                "isDarkMode" to isDarkMode
            ))
        }
    }
    mainViewModel.isInit = false


}

