package ru.blays.timetable.UI.Compose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.AppUpdater.web.api.HttpClient
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVMFactory
import ru.blays.timetable.UI.Compose.MainActivity.ObjectBox.objectBoxManager
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListVMFactory
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVMFactory
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableVMFactory
import ru.blays.timetable.UI.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.UI.DataClasses.buildTheme
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.RootElements

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
        )[GroupListScreenVM::class.java]
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (actionBar != null) {
            actionBar!!.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkPermissions(arrayOf(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Manifest.permission.MANAGE_EXTERNAL_STORAGE), 1234)
        }

        Log.d("HTTP_request_log", "start request")

        CoroutineScope(Dispatchers.IO).launch {

            Log.d("HTTP_request_log", "end request")
        }

        setContent {
            InitApp(
                mainViewModel,
                timetableViewModel,
                groupListViewModel,
                navigationViewModel,
                settingsViewModel
            )
            AviakatTimetableTheme(darkTheme = mainViewModel.isDarkMode, dynamicColor = mainViewModel.monetColors,
                mainViewModel.buildedTheme
            ) {
                mainViewModel.systemTheme = isSystemInDarkTheme()
                mainViewModel.init()
                RootElements(
                    mainViewModel,
                    timetableViewModel,
                    groupListViewModel,
                    navigationViewModel,
                    settingsViewModel
                )
            }
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
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    with(mainViewModel) {
        systemTheme = isSystemInDarkTheme()
        isDarkMode = when(initialSettings.appTheme) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
        monetColors = initialSettings.monetTheme ?: true

    }

    GlobalObserver(
        mainViewModel,
        timetableViewModel,
        groupListViewModel,
        navigationViewModel,
        settingsViewModel
    )
}

@Composable
fun GlobalObserver(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    // observe appBar state
    mainViewModel.navigateBackButtonVisible = navigationViewModel.currentScreen.Screen != ScreenList.MAIN_SCREEN
    mainViewModel.favoriteButtonVisible = navigationViewModel.currentScreen.Screen == ScreenList.TIMETABLE_SCREEN
    mainViewModel.favoriteButtonChecked = navigationViewModel.currentScreen.Key == mainViewModel.favoriteHref
    mainViewModel.subtitleVisible = mainViewModel.favoriteButtonVisible

    with(mainViewModel) {
        isDarkMode = when (settingsViewModel.themeSelectionState) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
    }

    with(mainViewModel) {
        when(navigationViewModel.currentScreen.Screen) {
            ScreenList.MAIN_SCREEN -> titleText = "Главная"
            ScreenList.ABOUT_SCREEN -> titleText = "О приложении"
            ScreenList.SETTINGS_SCREEN -> titleText = "Настройки"
            ScreenList.TIMETABLE_SCREEN -> {
                subtitleText = timetableViewModel.timetable.updateDate
                titleText = timetableViewModel.timetable.groupCode
            }
        }
    }

    // observe theme

    mainViewModel.monetColors = (settingsViewModel.monetTheme ?: mainViewModel.initialSettings.monetTheme)!!

    with(AccentColorList.list[settingsViewModel.accentColorIndex ?: mainViewModel.initialSettings.accentColor!!]) {
        if (!settingsViewModel.monetTheme!! || Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) mainViewModel.buildedTheme = buildTheme(
            colorDark = accentDark,
            lightColor = accentLight
        )
    }
}
