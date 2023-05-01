package ru.blays.timetable.UI.Compose.Root

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import ru.blays.timetable.UI.Compose.ComposeElements.UpdateInfo
import ru.blays.timetable.UI.Compose.Root.MainActivity.ObjectBox.objectBoxManager
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVMFactory
import ru.blays.timetable.UI.Compose.Screens.SimpleListScreen.GroupListVMFactory
import ru.blays.timetable.UI.Compose.Screens.SimpleListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.Compose.Screens.StatisticScreen.StatisticScreenViewModel
import ru.blays.timetable.UI.Compose.Screens.StatisticScreen.StatisticScreenViewModelFactory
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableVMFactory
import ru.blays.timetable.UI.Compose.Theme.AviakatTimetableTheme
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
        )[SimpleListScreenVM::class.java]
    }

    private val settingsViewModel by lazy {
        ViewModelProvider(
        this,
        SettingsScreenVMFactory(this)
        )[SettingsScreenVM::class.java]
    }

    private val statisticScreenViewModel by lazy {
        ViewModelProvider(
        this,
        StatisticScreenViewModelFactory()
        )[StatisticScreenViewModel::class.java]
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

        mainViewModel.init()

        setContent {
            AviakatTimetableTheme(
                darkTheme = mainViewModel.isDarkMode,
                dynamicColor = mainViewModel.monetColors,
                mainViewModel.buildedTheme
            ) {

                mainViewModel.systemTheme = isSystemInDarkTheme()

                RootElements(
                    mainViewModel = mainViewModel,
                    timetableViewModel = timetableViewModel,
                    groupListViewModel = groupListViewModel,
                    settingsViewModel = settingsViewModel,
                    statisticScreenViewModel = statisticScreenViewModel,
                    updateDialog = updateDialog
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